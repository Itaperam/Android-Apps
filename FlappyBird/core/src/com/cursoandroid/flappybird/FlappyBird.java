package com.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import javax.xml.soap.Text;

public class FlappyBird extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;

	//Atributos de configuração
	private int larguraDispositivo;
	private int alturaDispositivo;

	private float variacao = 0;
	private float velocidadeQueda = 0;
	private float posicaoInicialVertical;


	@Override
	public void create () {

		batch = new SpriteBatch();

		passaros = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");

		fundo = new Texture("fundo.png");

		//Captura as dimensões da tela
		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();

		posicaoInicialVertical = alturaDispositivo / 2;
	}

	@Override
	public void render () {

		variacao += Gdx.graphics.getDeltaTime() * 10; //Suaviza o movimento de bater asas
		velocidadeQueda++;
		if(variacao > 2) variacao = 0;

		if(Gdx.input.justTouched()) { // trantando o toque na tela
			velocidadeQueda = -15; //Passaro sobe
		}

		if(posicaoInicialVertical > 0 || velocidadeQueda < 0){
			posicaoInicialVertical-= velocidadeQueda; //Passaro cai
		}


		batch.begin();
		batch.draw(fundo,0,0, larguraDispositivo, alturaDispositivo);
		batch.draw(passaros[(int) variacao], 30, posicaoInicialVertical);
		batch.end();
	}
	
	/*@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}*/
}
