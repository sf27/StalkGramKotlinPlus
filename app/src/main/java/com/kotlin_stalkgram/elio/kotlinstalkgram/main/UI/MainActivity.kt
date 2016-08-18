package com.kotlin_stalkgram.elio.kotlinstalkgram.main.UI

import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.MediaController
import com.kotlin_stalkgram.elio.kotlinstalkgram.R
import com.kotlin_stalkgram.elio.kotlinstalkgram.StalkgramApp
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.DI.MainComponent
import com.kotlin_stalkgram.elio.kotlinstalkgram.main.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import javax.inject.Inject

open class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainView {

    @Inject
    lateinit var mainPresenter: MainPresenter

    lateinit var app: StalkgramApp
    lateinit var component: MainComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        app = (application as StalkgramApp?)!!
        setupInjection()

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.setDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        mainPresenter.onCreate()
        mainPresenter.checkIfStorageIsAvailable()

        setInputs(true)

        fab.setOnClickListener { onPaste() }
        btnShare.setOnClickListener { mainPresenter.shareFile() }
        btnSetAs.setOnClickListener { mainPresenter.setAsFile() }
    }

    private fun setupInjection() {
        component = app.getMainComponent(this, this)
        //        component.inject(this);
        mainPresenter = presenter
    }

    open val presenter: MainPresenter
        get() = component.presenter

    override fun onDestroy() {
        mainPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //        getMenuInflater().inflate(R.menu.main, menu);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_videos) {
        } else if (id == R.id.nav_images) {
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    open val clipBoardText: String
        get() {
            val myClipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            if (myClipboard.hasPrimaryClip()) {
                val abc = myClipboard.primaryClip
                val item = abc.getItemAt(0)
                return item.text.toString()
            }
            return ""
        }

    override fun enableInputs() {
        setInputs(true)
    }

    override fun disableInputs() {
        setInputs(false)
    }

    override fun showProgress() {
        runOnUiThread {
            imageView.visibility = View.INVISIBLE
            videoView.visibility = View.INVISIBLE
            setInputs(false)
            progressBar.isIndeterminate = false
            progressBar.progress = 0
            progressBar.max = 100
            progressBar.invalidate()
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun onProgress(progress: Int) {
        runOnUiThread { progressBar.progress = progress }
    }

    override fun hideProgress() {
        runOnUiThread {
            setInputs(true)
            imageView.visibility = View.INVISIBLE
            videoView.visibility = View.INVISIBLE
            progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onDownload() {
        onPaste()
    }

    override fun downloadImageSuccess(imagePath: String) {
        imageView.post {
            imageView.setImageDrawable(Drawable.createFromPath(imagePath))
            imageView.visibility = View.VISIBLE
        }
    }

    override fun downloadVideoSuccess(videoPath: String) {
        this.videoView.post {
            val mediaController: MediaController
            mediaController = MediaController(this@MainActivity, true)
            mediaController.isEnabled = false

            val lp = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)

            videoView.layoutParams = lp
            videoView.setMediaController(mediaController)
            videoView.setVideoPath(videoPath)
            videoView.setOnPreparedListener { mediaPlayer ->
                mediaController.isEnabled = true
                mediaPlayer.start()// starts playing the video
            }

            videoView.visibility = View.VISIBLE
        }
    }

    override fun downloadError(error: String) {
        runOnUiThread {
            txtLink.setText("")
            val msgError = String.format(
                    getString(R.string.main_error_downloading_file), error)
            Snackbar.make(
                    layoutMainContainer, msgError, Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    private fun setInputs(enabled: Boolean) {
        runOnUiThread {
            txtLink.isEnabled = enabled
            btnSetAs.isEnabled = enabled
            btnShare.isEnabled = enabled
        }
    }

    private fun onPaste() {
        val url = clipBoardText
        if (url.isEmpty() || !url.contains("https://www.instagram.com/")) {
            Snackbar.make(
                    layoutMainContainer,
                    R.string.main_error_empty_clipboard,
                    Snackbar.LENGTH_SHORT
            ).show()
            return
        }
        runOnUiThread {
            txtLink.setText(url)
        }
        mainPresenter.downloadFile(url)
    }
}
