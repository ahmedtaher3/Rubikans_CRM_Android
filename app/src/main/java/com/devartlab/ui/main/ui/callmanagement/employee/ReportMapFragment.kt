package com.devartlab.ui.main.ui.callmanagement.employee

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.devartlab.R
import com.devartlab.base.BaseFragment
import com.devartlab.databinding.FragmentReportMapBinding
import com.mapbox.mapboxsdk.annotations.IconFactory
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.geometry.LatLngBounds
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ID = "id"
private const val TIME = "time"
private const val SHIFT_ID = "shift_id"


class ReportMapFragment : BaseFragment<FragmentReportMapBinding>(),
    MapboxMap.OnMarkerClickListener {
    // TODO: Rename and change types of parameters
    private var id: String? = null
    private var time: String? = null
    private var shift_id: String? = null


    lateinit var binding: FragmentReportMapBinding
    lateinit var viewModel: EmployeeReportViewModel
    lateinit var queue: RequestQueue
    override fun getLayoutId(): Int {
        return R.layout.fragment_report_map
    }

    // private var mapView: MapView? = null
    private var map_boxMap: MapboxMap? = null

    val bounds = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ID)
            time = it.getString(TIME)
            shift_id = it.getString(SHIFT_ID)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = viewDataBinding!!
        viewModel = ViewModelProviders.of(this).get(EmployeeReportViewModel::class.java)
        queue = Volley.newRequestQueue(baseActivity)




        binding.mapView?.onCreate(savedInstanceState)
        binding.mapView?.getMapAsync { mapboxMap ->


            map_boxMap = mapboxMap
            map_boxMap!!.setPadding(100, 100, 100, 100)

            map_boxMap!!.getUiSettings().setZoomGesturesEnabled(true)
            map_boxMap!!.getUiSettings().setScrollGesturesEnabled(true)
            map_boxMap!!.getUiSettings().setAllGesturesEnabled(true)
            map_boxMap!!.getUiSettings().setRotateGesturesEnabled(true)
            map_boxMap!!.getUiSettings().getFocalPoint()
            map_boxMap!!.getUiSettings().setRotateVelocityAnimationEnabled(true)
            map_boxMap!!.getUiSettings().setTiltGesturesEnabled(true)
            map_boxMap!!.getUiSettings().setScaleVelocityAnimationEnabled(true)
            map_boxMap!!.getUiSettings().setCompassEnabled(false)
            map_boxMap!!.getUiSettings().setDeselectMarkersOnTap(true)
            map_boxMap!!.setOnMarkerClickListener(this)
            System.out.println(" getDailyReport " + id)

            viewModel.getDailyReport(false, id?.toInt()!!, time?.toLong()!!, shift_id?.toInt()!!)
            setObservers()


            mapboxMap.setStyle(Style.MAPBOX_STREETS) {

            }

        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReportMapFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(id: String, time: String, shift_id: String) =
            ReportMapFragment().apply {
                arguments = Bundle().apply {
                    putString(ID, id)
                    putString(TIME, time)
                    putString(SHIFT_ID, shift_id)
                }
            }
    }


    private fun setObservers() {

        viewModel.responseLiveDay.observe(baseActivity, Observer { it ->
            System.out.println("responseLive")

            var i = 0
            if (it.isSuccesed) {
                System.out.println("isSuccesed")
                if (it.data.employeeReport.isNullOrEmpty()) {

                } else {
                    System.out.println("employeeReport = " + it.data.employeeReport.size.toString())


                    for (model in it.data.startEndPoint) {

                        if (model.latVal == "0" || model.latVal == "1") {

                        } else {
                            try {
                                bounds.include(
                                    LatLng(
                                        model.latVal?.toDouble()!!,
                                        model.langVal?.toDouble()!!
                                    )
                                )

                                i += 1

                            } catch (e: Exception) {
                            }


                            if (model.isStartPoint == 1) {
                                try {

                                    val marker =  map_boxMap?.addMarker(
                                        MarkerOptions()
                                            .position(
                                                LatLng(
                                                    model.latVal?.toDouble()!!,
                                                    model.langVal?.toDouble()!!
                                                )
                                            )
                                            .title("start at " + model.startPointDateTime)
                                            .icon(
                                                IconFactory.getInstance(baseActivity)
                                                    .fromResource(R.drawable.mapbox_marker_green)
                                            )
                                    )


                                } catch (e: Exception) {
                                }
                            } else {
                                try {

                                    map_boxMap?.addMarker(
                                        MarkerOptions()
                                            .position(
                                                LatLng(
                                                    model.latVal?.toDouble()!!,
                                                    model.langVal?.toDouble()!!
                                                )
                                            )
                                            .title("end at " + model.startPointDateTime)
                                            .icon(
                                                IconFactory.getInstance(baseActivity)
                                                    .fromResource(R.drawable.mapbox_marker_gray)
                                            )
                                    )

                                } catch (e: Exception) {
                                }
                            }

                        }


                    }


                    for (model in it.data.employeeReport) {

                        if (model.visited) {


                            if (model.visitLat == "0" || model.visitLat == "1") {

                            } else {
                                System.out.println("aaaaaa" + model.visitLat + "\n" + model.visitLat + "\n")
                                try {
                                    bounds.include(
                                        LatLng(
                                            model.visitLat?.toDouble()!!,
                                            model.visitLang?.toDouble()!!
                                        )
                                    )

                                    i += 1

                                } catch (e: Exception) {
                                }




                                try {
                                    System.out.println("visitLat = " + model.visitLat.toString() + "visitLang = " + model.visitLang.toString())


                                    map_boxMap?.addMarker(
                                        MarkerOptions()
                                            .position(
                                                LatLng(
                                                    model.visitLat.toDouble(),
                                                    model.visitLang.toDouble()
                                                )
                                            )
                                            .title(model.doctor)
                                            .icon(
                                                IconFactory.getInstance(baseActivity)
                                                    .fromResource(R.drawable.mapbox_marker_icon_default)
                                            )
                                    )


                                } catch (e: Exception) {
                                }

                            }

                        }


                    }


                    Toast.makeText(baseActivity, i.toString(), Toast.LENGTH_SHORT).show()

                    if (i > 1) {
                        val cameraPosition2 =
                            map_boxMap?.getCameraForLatLngBounds(
                                bounds.build(),
                                intArrayOf(200, 200, 200, 200)
                            )

                        map_boxMap?.easeCamera(
                            CameraUpdateFactory.newCameraPosition(cameraPosition2!!),
                            2000
                        )

                    }


                }
            } else {

                Toast.makeText(baseActivity, it.rerurnMessage, Toast.LENGTH_SHORT).show()
            }


        })


    }


    fun getAddress(strLatitude: String, strLongitude: String): String? {
        val geocoder: Geocoder
        val addresses: List<Address>
        geocoder = Geocoder(activity, Locale("ar"))
        val latitude = strLatitude.toDouble()
        val longitude = strLongitude.toDouble()
        var address = ""
        try {
            addresses = geocoder.getFromLocation(
                latitude,
                longitude,
                1
            ) // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address =
                addresses[0].getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            //  throw RuntimeException(e.message)
        }
        return address
    }

    override fun onStart() {
        super.onStart()
        binding.mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView?.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView?.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView?.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView?.onDestroy()
    }

    override fun onMarkerClick(marker: com.mapbox.mapboxsdk.annotations.Marker): Boolean {

        getLocationFormGoogle(
            marker.position.latitude.toString() + "," + marker.position.longitude.toString(),
            marker.title
        )



        return true
    }


    fun getLocationFormGoogle(latLng: String, title: String): String {

        var address = ""
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET,
            "https://maps.googleapis.com/maps/api/geocode/json?latlng=$latLng&key=AIzaSyA0a4_5jHzBStit_c4_ZM7TPTCO-uNLfoM&language=ar-EG",
            null,
            Response.Listener<JSONObject>() {
                System.out.println(" jsonObjectRequest " + it.toString())

                if (it.optJSONArray("results").length() > 0) {

                    val results: JSONArray = it.getJSONArray("results")
                    val result = results.getJSONObject(0)

                    System.out.println(" getLocationFormGoogle " + result.getString("formatted_address"))


                    binding.address.text = (title + "\n" + result.getString("formatted_address"))

                } else {
                    System.out.println(" getLocationFormGoogle " + latLng)

                }

            }
            , Response.ErrorListener() {})

        queue.add(jsonObjectRequest)


        return address

    }

    fun getBitmapFromVectorDrawable(drawableId: Int): Bitmap? {
        var drawable: Drawable = ContextCompat.getDrawable(baseActivity, drawableId)!!
        val bitmap: Bitmap = Bitmap.createBitmap(
            drawable.getIntrinsicWidth(),
            drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
        drawable.draw(canvas)
        return bitmap
    }
}