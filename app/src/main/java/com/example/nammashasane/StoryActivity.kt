package com.example.nammashasane

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

class StoryActivity : AppCompatActivity() {

    private lateinit var txtLoading: TextView
    private lateinit var txtStory: TextView

    private lateinit var btnEnglish: Button
    private lateinit var btnKannada: Button
    private lateinit var btnMap: Button

    private lateinit var btnReport: Button

    private var englishStory = ""
    private var kannadaStory = ""

    private var detectedPlaceName = ""

    // Map Coordinates
    private var mapLat = 0.0
    private var mapLng = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_story)

        txtLoading =
            findViewById(R.id.txtLoading)

        txtStory =
            findViewById(R.id.txtStory)

        btnEnglish =
            findViewById(R.id.btnEnglish)

        btnKannada =
            findViewById(R.id.btnKannada)

        btnMap =
            findViewById(R.id.btnMap)

        btnReport =
            findViewById(R.id.btnReport)

        val imageData =
            intent.getStringExtra("IMAGE_DATA")

        txtLoading.text =
            "🔍 AI analyzing image..."

        try {

            // Gallery Image
            if (
                imageData != null &&
                imageData != "camera"
            ) {

                val image =
                    InputImage.fromFilePath(
                        this,
                        Uri.parse(imageData)
                    )

                val labeler =
                    ImageLabeling.getClient(
                        ImageLabelerOptions.DEFAULT_OPTIONS
                    )

                labeler.process(image)

                    .addOnSuccessListener { labels ->

                        txtLoading.text =
                            "✅ AI Analysis Completed"

                        var detectedPlace =
                            "karnataka"

                        for (label in labels) {

                            val text =
                                label.text.lowercase()

                            // Mysore
                            if (
                                text.contains("palace") ||
                                text.contains("castle")
                            ) {

                                detectedPlace = "mysore"
                                break
                            }

                            // Belur
                            else if (
                                text.contains("temple") ||
                                text.contains("architecture")
                            ) {

                                detectedPlace = "belur"
                                break
                            }

                            // Hampi
                            else if (
                                text.contains("monument") ||
                                text.contains("ruins")
                            ) {

                                detectedPlace = "hampi"
                                break
                            }

                            // Sculpture
                            else if (
                                text.contains("sculpture") ||
                                text.contains("statue")
                            ) {

                                detectedPlace = "halebidu"
                                break
                            }
                        }

                        generateStory(detectedPlace)
                    }

                    .addOnFailureListener {

                        txtLoading.text =
                            "❌ AI Detection Failed"
                    }

            } else {

                generateStory("karnataka")
            }

        } catch (e: Exception) {

            txtLoading.text =
                "❌ Error: ${e.message}"
        }

        // English Button
        btnEnglish.setOnClickListener {
            txtStory.text = englishStory
        }

        // Kannada Button
        btnKannada.setOnClickListener {
            txtStory.text = kannadaStory
        }

        // Open Map
        btnMap.setOnClickListener {

            val intent =
                Intent(this, MapsActivity::class.java)

            intent.putExtra("LAT", mapLat)
            intent.putExtra("LNG", mapLng)

            startActivity(intent)

        }
        btnReport.setOnClickListener {

            val intent =
                Intent(this, AlertActivity::class.java)

            intent.putExtra("IMAGE_URI", imageData)

            intent.putExtra(
                "PLACE_NAME",
                detectedPlaceName
            )

            startActivity(intent)
        }

}

    // Generate Story
    private fun generateStory(
        detectedPlace: String
    ) {
        detectedPlaceName = detectedPlace
        when (detectedPlace) {

            // Mysore
            "mysore" -> {

                mapLat = 12.3051
                mapLng = 76.6551

                englishStory =
                    """
                    🏰 Mysore Palace
                    
                    Mysore Palace is one of the most famous royal heritage sites in Karnataka.
                    
                    It represents the culture and architecture of the Wodeyar dynasty.
                    
                    📍 Mysuru, Karnataka
                    """.trimIndent()

                kannadaStory =
                    """
                    🏰 ಮೈಸೂರು ಅರಮನೆ
                    
                    ಮೈಸೂರು ಅರಮನೆ ಕರ್ನಾಟಕದ ಪ್ರಸಿದ್ಧ ರಾಜಮನೆತನದ ಸಾಂಸ್ಕೃತಿಕ ಸ್ಮಾರಕವಾಗಿದೆ.
                    
                    ಇದು ವಡೇಯರ್ ರಾಜವಂಶದ ವಾಸ್ತುಶಿಲ್ಪವನ್ನು ಪ್ರತಿನಿಧಿಸುತ್ತದೆ.
                    
                    📍 ಮೈಸೂರು, ಕರ್ನಾಟಕ
                    """.trimIndent()
            }

            // Belur
            "belur" -> {

                mapLat = 13.1620
                mapLng = 75.8650

                englishStory =
                    """
                    🏛 Belur Temple
                    
                    Belur Temple is a famous Hoysala architecture temple in Karnataka.
                    
                    It is known for detailed stone carvings and historical importance.
                    
                    📍 Belur, Karnataka
                    """.trimIndent()

                kannadaStory =
                    """
                    🏛 ಬೇಲೂರು ದೇವಸ್ಥಾನ
                    
                    ಬೇಲೂರು ದೇವಸ್ಥಾನವು ಹೊಯ್ಸಳರ ಪ್ರಸಿದ್ಧ ವಾಸ್ತುಶಿಲ್ಪದ ದೇವಸ್ಥಾನವಾಗಿದೆ.
                    
                    ಇದು ಸುಂದರ ಶಿಲ್ಪಕಲೆಗೆ ಪ್ರಸಿದ್ಧವಾಗಿದೆ.
                    
                    📍 ಬೇಲೂರು, ಕರ್ನಾಟಕ
                    """.trimIndent()
            }

            // Hampi
            "hampi" -> {

                mapLat = 15.3350
                mapLng = 76.4600

                englishStory =
                    """
                    🏺 Hampi Monument
                    
                    Hampi was the capital of the Vijayanagara Empire.
                    
                    It contains ancient temples, monuments, and ruins.
                    
                    📍 Hampi, Karnataka
                    """.trimIndent()

                kannadaStory =
                    """
                    🏺 ಹಂಪಿ ಸ್ಮಾರಕ
                    
                    ಹಂಪಿಯು ವಿಜಯನಗರ ಸಾಮ್ರಾಜ್ಯದ ರಾಜಧಾನಿಯಾಗಿತ್ತು.
                    
                    ಇಲ್ಲಿ ಪುರಾತನ ದೇವಾಲಯಗಳು ಮತ್ತು ಸ್ಮಾರಕಗಳಿವೆ.
                    
                    📍 ಹಂಪಿ, ಕರ್ನಾಟಕ
                    """.trimIndent()
            }

            // Halebidu
            "halebidu" -> {

                mapLat = 13.2120
                mapLng = 75.9910

                englishStory =
                    """
                    🗿 Halebidu Sculpture
                    
                    Halebidu is famous for Hoysala sculptures and temple carvings.
                    
                    The sculptures show ancient Karnataka art traditions.
                    
                    📍 Halebidu, Karnataka
                    """.trimIndent()

                kannadaStory =
                    """
                    🗿 ಹಳೆಬೀಡು ಶಿಲ್ಪ
                    
                    ಹಳೆಬೀಡು ಹೊಯ್ಸಳರ ಶಿಲ್ಪಕಲೆ ಮತ್ತು ದೇವಸ್ಥಾನಗಳಿಗೆ ಪ್ರಸಿದ್ಧವಾಗಿದೆ.
                    
                    ಇದು ಪುರಾತನ ಕರ್ನಾಟಕದ ಕಲೆಯನ್ನು ತೋರಿಸುತ್ತದೆ.
                    
                    📍 ಹಳೆಬೀಡು, ಕರ್ನಾಟಕ
                    """.trimIndent()
            }

            // Default
            else -> {

                mapLat = 12.9716
                mapLng = 77.5946

                englishStory =
                    """
                    🏛 Historical Place
                    
                    This image may belong to Karnataka historical heritage.
                    
                    📍 Karnataka
                    """.trimIndent()

                kannadaStory =
                    """
                    🏛 ಐತಿಹಾಸಿಕ ಸ್ಥಳ
                    
                    ಈ ಚಿತ್ರವು ಕರ್ನಾಟಕದ ಐತಿಹಾಸಿಕ ಪರಂಪರೆಗೆ ಸಂಬಂಧಿಸಿರಬಹುದು.
                    
                    📍 ಕರ್ನಾಟಕ
                    """.trimIndent()
            }
        }
        txtStory.text = englishStory
    }
}