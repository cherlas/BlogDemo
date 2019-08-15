package cn.istarx.bubbledemo

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID: String = "bubble_channel_id"
    private val CHANNEL_NAME: String = "bubble_channel_name"
    private val CHANNEL_TAG: String = "bubble_channel_tag"
    private val PERSON_NAME: String = "Person Name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        send_timely_notification.setOnClickListener { sendNotification() }
        // send_background_notification.setOnClickListener { sendNotification() }
    }

    /*private fun sendNotification(isTimely: Boolean) {
        val notificationManager: NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH))

        val target = Intent(applicationContext, BubbleLoginActivity::class.java)
        val bubbleIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, target, 0)

        val bubbleMetadata = Notification.BubbleMetadata.Builder()
                .setIntent(bubbleIntent)
                .setDesiredHeight(600)
                .setIcon(Icon.createWithResource(applicationContext, R.drawable.ic_notifications))
                // setup setAutoExpandBubble
                .setAutoExpandBubble(true)
                .setSuppressNotification(true)
                .build()
        val person: Person = Person.Builder().setBot(true).setImportant(true).setName(CHANNEL_NAME).build()

        val builder = Notification.Builder(applicationContext, CHANNEL_ID)
                .setContentIntent(bubbleIntent)
                .setContentTitle(resources.getString(R.string.notification))
                .setContentText(resources.getString(R.string.notification_contnet_text))
                .setBubbleMetadata(bubbleMetadata)
                .setSmallIcon(R.drawable.ic_notifications)


        if (!isTimely) {
            builder.style = Notification.MessagingStyle(person)
                    .addMessage(Notification.MessagingStyle.Message("Message Text", System.currentTimeMillis(), person))
            builder.addPerson(person)
        }

        val notification = builder.build()
        if (!isTimely) {
            Handler(mainLooper).postDelayed({ notificationManager.notify(CHANNEL_TAG, 0, notification) }, 2000)
            pressHome()
        } else {
            notificationManager.notify(CHANNEL_TAG, 0, notification)
        }
    }*/

    private fun sendNotification() {
        val notificationManager: NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH))

        val target = Intent(applicationContext, BubbleLoginActivity::class.java)
        val bubbleIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, target, 0)

        val bubbleMetadata = Notification.BubbleMetadata.Builder()
                .setIntent(bubbleIntent)
                .setDesiredHeight(600)
                .setIcon(Icon.createWithResource(applicationContext, R.drawable.ic_notifications))
                .setAutoExpandBubble(true)
                .setSuppressNotification(true)
                .build()

        val notification = Notification.Builder(applicationContext, CHANNEL_ID)
                .setContentIntent(bubbleIntent)
                .setContentTitle(resources.getString(R.string.notification))
                .setContentText(resources.getString(R.string.notification_contnet_text))
                .setBubbleMetadata(bubbleMetadata)
                .setSmallIcon(R.drawable.ic_notifications)
                .build()

        notificationManager.notify(CHANNEL_TAG, 0, notification)
    }

    /*fun pressHome() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
        applicationContext.startActivity(intent)
    }*/
}
