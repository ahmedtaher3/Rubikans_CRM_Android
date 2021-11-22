package com.devartlab.utils

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.text.format.DateFormat
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.devartlab.data.room.plan.PlanEntity
import com.devartlab.data.shared.DataManager
import com.devartlab.model.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.security.ProviderInstaller
import com.huawei.hms.api.HuaweiApiAvailability
import java.io.*
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import kotlin.collections.ArrayList


private const val TAG = "CommonUtilities"
object CommonUtilities {
    /**
     * check if device is tablet
     *
     * @param ctx
     * @return
     */


    fun checkIsTablet(ctx: Context): Boolean {
        return ctx.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    fun getTextAfterSlash(txt: String): String {

        System.out.println(" getTextAfterSlash " + txt.replace("[^\\d:]".toRegex(), ""))

        val parts = txt.split("/");
        return parts[0].replace(" ", "")
    }

    fun getPlanText1(txt: String): String {


        val parts = txt.split("ActualTime:");
        return parts[0]
    }

    fun getPlanText2(txt: String): String {

        val parts = txt.split("ActualTime:");
        return parts[1]
    }


    fun getDifferenceTime(reportStart: String, startAt: String): Long {
        val simpleDateFormat = SimpleDateFormat("hh:mma")

        val date1 = simpleDateFormat.parse(reportStart)
        val date2 = simpleDateFormat.parse(startAt)

        val difference: Long = date2.getTime() - date1.getTime()


        return (difference / 1000) / 60
    }

    fun isFake(ctx: Context, location: Location?): Boolean {
        var isMock = false
        isMock = if (Build.VERSION.SDK_INT >= 18) {
            location?.isFromMockProvider!!
        }
        else {
            Settings.Secure.getString(ctx.contentResolver, Settings.Secure.ALLOW_MOCK_LOCATION) !== "0"
        }
        return isMock
    }

    fun getLineNumber(): Int {
        return Thread.currentThread().stackTrace[2].lineNumber
    }



    fun saveToInternalStorage(context: Context, bitmapImage: Bitmap, position: Int): String? {

        val name = position.toString() + "_" + System.currentTimeMillis().toString()
        val cw = ContextWrapper(context) // path to /data/data/yourapp/app_data/imageDir
        val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE) // Create imageDir
        val mypath = File(directory, name)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath) // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
        finally {
            try {
                fos?.close()
            }
            catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.getAbsolutePath() + "/" + name
    }

    fun checkIsDeveloperMoodOn(ctx: Context): Int {
        return Settings.Secure.getInt(ctx.contentResolver,
                Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, 0)
    }

    fun isMockLocationOn(location: Location, context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            location.isFromMockProvider
        } else {
            var mockLocation = "0"
            try {
                mockLocation = Settings.Secure.getString(context.contentResolver, Settings.Secure.ALLOW_MOCK_LOCATION)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            mockLocation != "0"
        }
    }

    fun getCustomImagePath(context: Context?, fileName: String?): File? {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state) {
            val filePath = Environment.getExternalStorageDirectory().path
            val myDir = File(filePath, "Devart")
            myDir.mkdirs()
            var fname: String? = null
            fname = if (fileName != null) "$fileName.png" else Calendar.getInstance().timeInMillis.toString() + ".png"
            val file = File(myDir, fname)
            if (file.exists()) file.delete()

            //return (file.getAbsolutePath());
            return file
        } else {
            Toast.makeText(context, "Sd Card is not mounted", Toast.LENGTH_LONG).show()
        }
        return null
    }

    fun DecodeFile(id: String, path: String?, DESIREDWIDTH: Int, DESIREDHEIGHT: Int): String {
        var strMyImagePath: String? = null
        var scaledBitmap: Bitmap? = null
        try {
            // Part 1: Decode image
            val unscaledBitmap = ScalingUtilities.decodeFile(path, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT)
            scaledBitmap = if (!(unscaledBitmap.width <= DESIREDWIDTH && unscaledBitmap.height <= DESIREDHEIGHT)) {
                // Part 2: Scale image
                ScalingUtilities.createScaledBitmap(unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT)
            } else {
                unscaledBitmap.recycle()
                return path!!
            }

            // Store to tmp file
            val extr = Environment.getExternalStorageDirectory().toString()
            val mFolder = File("$extr/TMMFOLDER")
            if (!mFolder.exists()) {
                mFolder.mkdir()
            }
            val s = id + "_" + currentToMillis.toString() + "_devart.png"
            val f = File(mFolder.absolutePath, s)
            strMyImagePath = f.absolutePath
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(f)
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos)
                fos.flush()
                fos.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            scaledBitmap.recycle()
        } catch (e: Throwable) {
        }
        return strMyImagePath ?: path!!
    }

    fun GetPathFromUri(context: Context, uri: Uri): String? {
        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }

                // TODO handle non-primary volumes
            } else if (isDownloadsDocument(uri)) {
                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))
                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }
                val selection = "_id=?"
                val selectionArgs = arrayOf(
                        split[1]
                )
                return getDataColumn(context, contentUri, selection, selectionArgs)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {

            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            return uri.path
        }
        return null
    }

    fun getDataColumn(context: Context, uri: Uri?, selection: String?,
                      selectionArgs: Array<String>?): String? {
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(
                column
        )
        try {
            cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs,
                    null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority
    }

    fun convertArrayToCommaSeparatedString(ids: ArrayList<Int?>?): String {
        return TextUtils.join(",", ids!!)
    }

    @JvmStatic
    val randomColor: Int
        get() {
            val rnd = Random()
            return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        }

    /**
     * check if date 1 same day from date 2
     *
     * @param date1
     * @param date2
     * @return
     */
    fun isSameDay(date1: Date?, date2: Date?): Boolean {
        val fmt = SimpleDateFormat("yyyyMMdd", Locale.US)
        return fmt.format(date1) == fmt.format(date2)
    }

    fun isSameDayMeals(date1: Date?, date2S: String?): Boolean {
        val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        var date2: Date? = null
        try {
            date2 = fmt.parse(date2S)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = date1
        cal2.time = date2
        return cal1[Calendar.DAY_OF_YEAR] == cal2[Calendar.DAY_OF_YEAR] &&
                cal1[Calendar.YEAR] == cal2[Calendar.YEAR]
    }

    /**
     * get day name from Milliseconds
     *
     * @param Long
     * @return
     */
    fun getDayName(Long: Long): String {
        return DateFormat.format("EEEE", Date(Long)).toString()
    }

    fun isGooglePlayServicesAvailable(activity: Activity?): Boolean {

        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(activity)
        return status == ConnectionResult.SUCCESS

    }

    fun isHuaweiServicesAvailable(activity: Activity?): Boolean {

        val googleApiAvailability = HuaweiApiAvailability.getInstance()
        val status = googleApiAvailability.isHuaweiMobileServicesAvailable(activity)
        return status == com.huawei.hms.api.ConnectionResult.SUCCESS

    }

    /**
     * get Customers Ides in Comma Separated String from plan list
     *
     * @param ids
     * @return
     */
    fun getCusIdes(ids: List<PlanEntity?>?): String {
        val integers = ArrayList<Int?>()
        for (i in ids!!) {
            integers.add(i?.customerid)
        }
        return TextUtils.join(",", integers)
    }

    /**
     * get Branch Ides in Comma Separated String from plan list
     *
     * @param ids
     * @return
     */
    fun getBranchIdes(ids: List<PlanEntity?>?): String {
        val integers = ArrayList<Int?>()
        for (i in ids!!) {
            integers.add(i?.customerBranchid)
        }
        return TextUtils.join(",", integers)
    }

    fun getDoubleVisitsEmpsIdes(ids: List<PlanEntity?>?): String {
        val integers = ArrayList<Int?>()
        for (i in ids!!) {
            if (i?.visit!!) {
                if (!integers.contains(i?.doubleVAccountId)) integers.add(i?.doubleVAccountId)

            }
        }
        return TextUtils.join(",", integers)
    }

    fun checkIfTextEmpty(s: String): String {
        return if (TextUtils.isEmpty(s)) {
            "0"
        } else {
            s
        }
    }

    @JvmStatic
    fun checkTwoDigits(n: String): String {
        return if (n.length == 1) {
            "0$n"
        } else {
            n
        }
    }

    fun convertToDateTime(milli: Long): String {
        val fmt = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US)
        return fmt.format(milli)
    }

    fun convertToDate(milli: Long): String {
        val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return fmt.format(milli)
    }


    fun getCurrentDate(): String {
        val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return fmt.format(System.currentTimeMillis())
    }

    fun getDifferenceDays(d1: Date, d2: Date): Long {
        val diff = d2.time - d1.time
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
    }

    fun getVersionName(context: Context): String {
        return try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            ""
        }
    }

    fun convertToMillis(oldTime: String?): Long {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        formatter.isLenient = false
        var oldDate: Date? = null
        try {
            oldDate = formatter.parse(oldTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val oldMillis = oldDate!!.time
        return oldMillis + 14400000
    }

    fun convertFullDateToMillis(date: String?): Long {
        val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.US)
        formatter.isLenient = false
        var oldDate: Date? = null
        try {
            oldDate = formatter.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val oldMillis = oldDate!!.time
        return oldMillis + 14400000
    }

    fun convertDateToMillis(date: String?): Long {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        formatter.isLenient = false
        var oldDate: Date? = null
        try {
            oldDate = formatter.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val oldMillis = oldDate!!.time
        return oldMillis + 14400000
    }

    fun convertServerDateToMillis(date: String?): Long {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.US)
        formatter.isLenient = false
        var oldDate: Date? = null
        try {
            oldDate = formatter.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val oldMillis = oldDate!!.time
        return oldMillis + 14400000
    }

    val currentToMillis: Long
        get() {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val DATE = formatter.format(System.currentTimeMillis())
            formatter.isLenient = false
            var oldDate: Date? = null
            try {
                oldDate = formatter.parse(DATE)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val oldMillis = oldDate!!.time
            return oldMillis + 14400000
        }

    fun getRequestsIds(myData: ArrayList<Detail>): String {
        val integers = ArrayList<Int?>()
        for (i in myData) {
            if (i.selected) integers.add(i.docWorkFlowDetId)
        }
        return TextUtils.join(",", integers)
    }

    fun initializeSSLContext(mContext: Context) {
        try {
            SSLContext.getInstance("TLSv1.2")
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        try {
            ProviderInstaller.installIfNeeded(mContext.applicationContext)
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
    }

    fun sendMessage(context: Context?, b: Boolean?) {
        Log.d("sender", "Broadcasting message")
        val intent = Intent("update-counter")
        // You can also include some extra data.
        intent.putExtra("count", b)
        LocalBroadcastManager.getInstance(context!!).sendBroadcast(intent)
    }


    fun writeToSDFile(text: String): String {

        // Find the root of the external storage.
        // See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal
        val root = Environment.getExternalStorageDirectory()
        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder
        val dir = File(root.absolutePath + "/devartlab")
        dir.mkdirs()
        val file = File(dir, "myData.txt")
        try {
            val f = FileOutputStream(file)
            val pw = PrintWriter(f)
            pw.println(text)
            pw.flush()
            pw.close()
            f.close()
            return file.path
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Log.i(" writeToSDFile ", "******* File not found. Did you" +
                    " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?")
            return ""
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        }


    }

    fun getText(): String? {


        val root = Environment.getExternalStorageDirectory()
        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder
        val dir = File(root.absolutePath + "/devartlab")
        dir.mkdirs()
        val file = File(dir, "myData.txt")

        if (!file.exists())
        {
            Log.d(TAG, "getText: empty")
            return null
        }

        val bufferedReader = BufferedReader(FileReader(file))
        var read: String?
        val builder = java.lang.StringBuilder("")

        while (bufferedReader.readLine().also { read = it } != null) {
            builder.append(read)

        }
        Log.d("Output", builder.toString())
        bufferedReader.close()

        return builder.toString()
    }
}