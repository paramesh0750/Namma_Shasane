package com.example.nammashasane

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthorityActivity : AppCompatActivity() {

    private lateinit var listViewReports: ListView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_authority)

        listViewReports =
            findViewById(R.id.listViewReports)

        loadReports()
    }

    private fun loadReports() {

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val reports =
                    DatabaseProvider
                        .getDatabase(this@AuthorityActivity)
                        .alertDao()
                        .getAllAlerts()

                val reportList =
                    ArrayList<String>()

                for (report in reports) {

                    reportList.add(
                        "📍 ${report.placeName}\n⚠ ${report.issueType}"
                    )
                }

                runOnUiThread {

                    val adapter =
                        ArrayAdapter(
                            this@AuthorityActivity,
                            android.R.layout.simple_list_item_1,
                            reportList
                        )

                    listViewReports.adapter =
                        adapter
                }

            } catch (e: Exception) {

                runOnUiThread {

                    Toast.makeText(
                        this@AuthorityActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}