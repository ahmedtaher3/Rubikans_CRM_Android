package com.devartlab.ui.main.ui.contactlist.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.devartlab.R

class ContactDetails : AppCompatActivity() {
    private val contactsRecyclerAdapter: GetContactsRecyclerAdapter? = null
    var nametex: TextView? = null
    var emailtex: TextView? = null
    var phone1tex: TextView? = null
    var phone2tex: TextView? = null
    var titletex: TextView? = null
    var deptex: TextView? = null
    var name: String? = null
    var email: String? = null
    var phone1: String? = null
    var phone2: String? = null
    var titel: String? = null
    var department: String? = null
    var savecontact: TextView? = null
    var firstlitter_fromname: TextView? = null
    var linear_phone2: LinearLayout? = null
    var linear_email: LinearLayout? = null
    private var toolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)
        nametex = findViewById(R.id.cont_name_det)
        emailtex = findViewById(R.id.cont_email_det)
        phone1tex = findViewById(R.id.cont_phone1_det)
        phone2tex = findViewById(R.id.cont_phone2_det)
        titletex = findViewById(R.id.cont_title_det)
        deptex = findViewById(R.id.cont_dep_det)
        savecontact = findViewById(R.id.save_contact)
        firstlitter_fromname = findViewById(R.id.firstlitter_fromname)
        linear_phone2 = findViewById(R.id.linear_phone2)
        linear_email = findViewById(R.id.linear_email)
        name = intent.getStringExtra("name")
        email = intent.getStringExtra("email")
        phone1 = intent.getStringExtra("phone1")
        phone2 = intent.getStringExtra("phone2")
        titel = intent.getStringExtra("titel")
        department = intent.getStringExtra("dep")

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = name


        nametex?.setText(name.toString())
        firstlitter_fromname?.setText(name?.substring(0, 1))
        emailtex?.setText("  " + email.toString())
        phone1tex?.setText("  " + phone1.toString())
        phone2tex?.setText("  " + phone2.toString())
        titletex?.setText(titel.toString())
        deptex?.setText(department.toString())
        replaceifempty()
        if (phone1?.startsWith("0")!! && phone2?.startsWith("0")!!) {
            phone1tex?.setText("   $phone1")
            phone2tex?.setText("   $phone2")
        } else if (!phone1?.startsWith("0")!!) {
            phone1tex?.setText("    0$phone1")
        }
        if (!phone2?.startsWith("0")!!) {
            phone2tex?.setText("    0$phone2")
        }
        savecontact?.setOnClickListener(View.OnClickListener {
            val intent = Intent(ContactsContract.Intents.Insert.ACTION)
            intent.type = ContactsContract.RawContacts.CONTENT_TYPE
            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email)
            intent.putExtra(ContactsContract.Intents.Insert.NAME, name)
            intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, titel)
            intent.putExtra(ContactsContract.Intents.Insert.COMPANY, department)
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone1tex?.getText().toString())
            if (phone2?.startsWith("0")!!) {
                phone2tex?.setText("    0$phone2")
                intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, phone2tex?.getText().toString())
            }
            startActivity(intent)
        })
        emailtex?.setOnClickListener(View.OnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", emailtex?.getText().toString(), null))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        })
        phone2tex?.setOnClickListener(View.OnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:" + phone2tex?.getText()) //change the number.
            startActivity(callIntent)
        })
        phone1tex?.setOnClickListener(View.OnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:" + phone1tex?.getText()) //change the number.
            startActivity(callIntent)
        })
    }

    private fun replaceifempty() {
        if (name == "") {
            nametex!!.text = "        ______"
        }
        if (email == "") {
            emailtex!!.text = "        ______"
            emailtex!!.visibility = View.GONE
            linear_email!!.visibility = View.GONE
        }
        if (phone1 == "") {
            phone1tex!!.text = "        ______"
        }
        if (phone2 == "") {
            phone2tex!!.text = "        ______"
            phone2tex!!.visibility = View.GONE
            linear_phone2!!.visibility = View.GONE
        }
        if (titel == "") {
            titletex!!.text = "        ______"
        }
        if (department == "") {
            deptex!!.text = "        ______"
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}