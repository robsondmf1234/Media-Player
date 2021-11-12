package com.example.mediaplayer

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.mediaplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var audioManager: AudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Iniciando o player Ex:MediaPlayer.create(contexto , musica)
        mediaPlayer = MediaPlayer.create(applicationContext, R.raw.teste)

        inicializarSeekBar()
        setupButtonPlay()
        setupButtonPause()
        setupButtonStop()
    }

    //Método opcional (ciclo de vida), quando o app for minimizado, a musica é pausada
    override fun onStop() {
        super.onStop()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        }
    }

    //Método opcional (ciclo de vida), quando o app for restaurado , retoma a musica
    override fun onRestart() {
        super.onRestart()
        if (this.mediaPlayer != null) {
            mediaPlayer.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this.mediaPlayer != null && mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            //Libera recursos de media que estao sendo executadas no mediaplayer
            mediaPlayer.release()
        }
    }

    private fun inicializarSeekBar() {
        binding.seekVolume


        //Configura o audio manager
        //Recuperando o serviço de audio
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        //Recupera os valores de volume máximo e o volume atual

        //Recupera o volume maximo para musicas
        val volumeMaximo: Int = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val volumeAtual: Int = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        //Setando o valor maximo de volume do sistema, na propriedade da seekBar
        binding.seekVolume.max = volumeMaximo

        //Setando na seta da seekBar o volume atual do sistema
        binding.seekVolume.progress = volumeAtual

        binding.seekVolume.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //Mudando o volume pelo seekBar
                //Ex:audioManager.setStreamVolume(Propriedade para mudar o volume do audiomanager , variavel que esta na seekbar que será o volume , AudioManager.FLAG_SHOW_UI mostra na Ui a mudança de volume)
                audioManager.setStreamVolume(
                    AudioManager.STREAM_MUSIC,
                    progress,
                    AudioManager.FLAG_SHOW_UI
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun setupButtonStop() {
        binding.buttonStop.setOnClickListener {
            paraMusica()
        }
    }

    private fun setupButtonPause() {
        binding.buttonPause.setOnClickListener {
            pausarMusica()
        }
    }

    private fun setupButtonPlay() {
        binding.buttonPlay.setOnClickListener {
            executarSom()
        }
    }

    private fun paraMusica() {
        //Verificando se o player está realmente tocando a musica
        if (mediaPlayer.isPlaying) {
            //Método para parar a musica
            mediaPlayer.stop()
            //Recriando o palyer e carregamendo a musica novamente
            mediaPlayer = MediaPlayer.create(applicationContext, R.raw.teste)
        }
    }

    private fun executarSom() {
        //Verificando se o player não esta como null
        if (mediaPlayer != null) {
            //Método para executar a musica
            mediaPlayer.start()
        }
    }

    private fun pausarMusica() {
        //Verificando se o player está realmente tocando a musica
        if (mediaPlayer.isPlaying) {
            //Método para pausar a musica
            mediaPlayer.pause()
        }
    }

}