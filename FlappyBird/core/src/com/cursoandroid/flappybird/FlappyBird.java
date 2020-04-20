package com.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

import javax.xml.soap.Text;

public class FlappyBird extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	private Texture canoBaixo;
	private Texture canoTopo;
	private Random numeroRandomico;
	private BitmapFont fonte;

	//Atributos de configuração
	private int larguraDispositivo;
	private int alturaDispositivo;
	private int estadoDoJogo=0;
	private int pontuacao=0;

	private float variacao = 0;
	private float velocidadeQueda = 0;
	private float posicaoInicialVertical;
	private float posicaoMovimentocanoHorizontal;
	private float espacaoEntreCanos;
	private float deltaTime;
	private float alturaEntreCanosRandomica;
	private  boolean marcouPonto =  false;


	@Override
	public void create () {

		batch = new SpriteBatch();
		numeroRandomico = new Random();
		fonte = new BitmapFont(); //Desenhando a pontuação
		fonte.setColor(Color.WHITE);
		fonte.getData().setScale(6);

		passaros = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");

		fundo = new Texture("fundo.png");
		canoBaixo = new Texture("cano_baixo.png");
		canoTopo = new Texture("cano_topo.png");

		//Captura as dimensões da tela
		larguraDispositivo = Gdx.graphics.getWidth();
		alturaDispositivo = Gdx.graphics.getHeight();

		posicaoInicialVertical = alturaDispositivo / 2;
		posicaoMovimentocanoHorizontal = larguraDispositivo;
		espacaoEntreCanos = 300;
	}

	@Override
	public void render () {

		deltaTime = Gdx.graphics.getDeltaTime();
		variacao += deltaTime * 10; //Suaviza o movimento de bater asas

		if (variacao > 2) variacao = 0;

	    if(estadoDoJogo == 0){ //Condição de iniciar o jogo
            if(Gdx.input.justTouched()){
                estadoDoJogo = 1;
            }
        }
	    else {
            posicaoMovimentocanoHorizontal -= deltaTime * 200; //Movimentando os canos
            velocidadeQueda++;

            if (Gdx.input.justTouched()) { // trantando o toque na tela
                velocidadeQueda = -15; //Passaro sobe
            }

            if (posicaoInicialVertical > 0 || velocidadeQueda < 0) {
                posicaoInicialVertical -= velocidadeQueda; //Passaro cai
            }

            //Verifica se o cano saiu completamente da tela
            if (posicaoMovimentocanoHorizontal < -canoTopo.getWidth()) {
                posicaoMovimentocanoHorizontal = larguraDispositivo;
                alturaEntreCanosRandomica = numeroRandomico.nextInt(400) - 200;
				marcouPonto = false;
            }
            //verifica pontuação
			if(posicaoMovimentocanoHorizontal < 120){
				if (!marcouPonto){
					pontuacao++;
					marcouPonto = true;
				}
			}
        }

            batch.begin();
            batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
            batch.draw(canoTopo, posicaoMovimentocanoHorizontal, alturaDispositivo / 2 + espacaoEntreCanos / 2 + alturaEntreCanosRandomica);
            batch.draw(canoBaixo, posicaoMovimentocanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacaoEntreCanos / 2 + alturaEntreCanosRandomica);
            batch.draw(passaros[(int) variacao], 120, posicaoInicialVertical);
            fonte.draw(batch, String.valueOf(pontuacao), larguraDispositivo - larguraDispositivo + 40, alturaDispositivo-50 );
            batch.end();

	}
	
	/*@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}*/
}
