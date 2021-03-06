package com.shuvornb.cardgame

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size
import java.io.IOException

class MainActivity : AppCompatActivity() {

    var cardRandomizer = CardRandomizer()
    var scoreCalculator = ScoreCalculator()
    var largestPrime = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDisplayCards.setOnClickListener {

            cardRandomizer.initializeDeckOfCards()
            var randomCards = cardRandomizer.getFourRandomCards()

            txtViewScore.text = "Score: 0"
            txtViewMaxScore.text = "Max Score: 0"

            if(randomCards.isEmpty()) {
                Toast.makeText(this, "All cards have been displayed!", Toast.LENGTH_SHORT).show()
                txtViewScore.text = "Total Score: " + scoreCalculator.globalScore.toString()
            }
            else {

                largestPrime = scoreCalculator.calculateHighestPrime(randomCards)

                setColorOfAnImageView(imgViewCard1, 0)
                val assetsBitmap1: Bitmap? = getBitmapFromAssets(randomCards[0] + ".png")
                imgViewCard1.setImageBitmap(assetsBitmap1)
                imgViewCard1.setTag(imgViewCard1.id, randomCards[0])

                setColorOfAnImageView(imgViewCard2, 0)
                val assetsBitmap2: Bitmap? = getBitmapFromAssets(randomCards[1] + ".png")
                imgViewCard2.setImageBitmap(assetsBitmap2)
                imgViewCard2.setTag(imgViewCard2.id, randomCards[1])

                setColorOfAnImageView(imgViewCard3, 0)
                val assetsBitmap3: Bitmap? = getBitmapFromAssets(randomCards[2] + ".png")
                imgViewCard3.setImageBitmap(assetsBitmap3)
                imgViewCard3.setTag(imgViewCard3.id, randomCards[2])

                setColorOfAnImageView(imgViewCard4, 0)
                val assetsBitmap4: Bitmap? = getBitmapFromAssets(randomCards[3] + ".png")
                imgViewCard4.setImageBitmap(assetsBitmap4)
                imgViewCard4.setTag(imgViewCard4.id, randomCards[3])
            }
        }

        imgViewCard1.setOnClickListener {
            //Toast.makeText(this, imgViewCard1.getTag(imgViewCard1.id).toString() + "Button", Toast.LENGTH_SHORT).show()
            scoreCalculator.addScore(imgViewCard1.getTag(imgViewCard1.id).toString())
            setColorOfAnImageView(imgViewCard1, 50)
        }

        imgViewCard2.setOnClickListener {
            scoreCalculator.addScore(imgViewCard2.getTag(imgViewCard2.id).toString())
            setColorOfAnImageView(imgViewCard2, 50)
        }

        imgViewCard3.setOnClickListener {
            scoreCalculator.addScore(imgViewCard3.getTag(imgViewCard3.id).toString())
            setColorOfAnImageView(imgViewCard3, 50)
        }

        imgViewCard4.setOnClickListener {
            scoreCalculator.addScore(imgViewCard4.getTag(imgViewCard4.id).toString())
            setColorOfAnImageView(imgViewCard4, 50)
        }

        btnCalculateScore.setOnClickListener {
            scoreCalculator.calculateScore()
            val scoreOfThisRound = scoreCalculator.lastRoundScore.toString()

           // Log.i("SCORE", scoreCalculator.globalScore)
            txtViewScore.text = "Score: $scoreOfThisRound"
            txtViewMaxScore.text = "Max Score: $largestPrime"

            if(scoreOfThisRound.toInt() == largestPrime) {
                viewKonfetti.build()
                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.RECT, Shape.CIRCLE)
                    .addSizes(Size(12))
                    .setPosition(-50f, viewKonfetti.width + 50f, -50f, -50f)
                    .streamFor(300, 5000L)

            }
        }
    }

    // Custom method to get assets folder image as bitmap
    private fun getBitmapFromAssets(fileName: String): Bitmap? {
        return try {
            BitmapFactory.decodeStream(assets.open(fileName))
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    // change color of an ImageView
    private fun setColorOfAnImageView(imgView: ImageView, transparency: Int) {
        imgView.setColorFilter(Color.argb(transparency,0,0,0))
    }
}
