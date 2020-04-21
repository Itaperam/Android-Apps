package com.cursoandroid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

import javax.xml.soap.Text;

public class FlappyBird extends ApplicationAdapter {

	private SpriteBatch batch;
	private Texture[] passaros;
	private Texture fundo;
	private Texture canoBaixo;
	private Texture canoTopo;
	private Texture gameOver;
	private Random numeroRandomico;
	private BitmapFont fonte;
	private BitmapFont mensagem;
	private Circle passarocirculo;
	private Rectangle retanguloCanoTopo;
	private Rectangle retanguloCanoBaixo;
	//private ShapeRenderer shape;

	//Atributos de configuração
	private float larguraDispositivo;
	private float alturaDispositivo;
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

	//Câmera
	private OrthographicCamera camera;
	private Viewport viewport;
	private final float VIRTUAL_WIDTH = 980;
	private final float VIRTUAL_HEIGHT = 1024;


	@Override
	public void create () {

		batch = new SpriteBatch();
		numeroRandomico = new Random();

		passarocirculo = new Circle();
		/*retanguloCanoBaixo = new Rectangle();
		retanguloCanoTopo = new Rectangle();
		shape = new ShapeRenderer();*/

		fonte = new BitmapFont(); //Desenhando a pontuação
		fonte.setColor(Color.WHITE);
		fonte.getData().setScale(6);

		mensagem = new BitmapFont(); //Desenhando a mensagem
		mensagem.setColor(Color.WHITE);
		mensagem.getData().setScale(3);


		passaros = new Texture[3];
		passaros[0] = new Texture("passaro1.png");
		passaros[1] = new Texture("passaro2.png");
		passaros[2] = new Texture("passaro3.png");

		fundo = new Texture("fundo.png");
		canoBaixo = new Texture("cano_baixo.png");
		canoTopo = new Texture("cano_topo.png");
		gameOver = new Texture("game_over.png");

		/************************************************
		 * Configurações da câmera
		 */
		camera = new OrthographicCamera();
		camera.position.set(VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2, 0);
		viewport = new StretchViewport(VIRTUAL_WIDTH, VIRTUAL_WIDTH, camera);

		//Captura as dimensões da tela
		larguraDispositivo = VIRTUAL_WIDTH;
		alturaDispositivo = VIRTUAL_HEIGHT;

		posicaoInicialVertical = alturaDispositivo / 2;
		posicaoMovimentocanoHorizontal = larguraDispositivo;
		espacaoEntreCanos = 300;
	}

	@Override
	public void render () {

		camera.update();
		//Limpar frames anteriores
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		deltaTime = Gdx.graphics.getDeltaTime();
		variacao += deltaTime * 10; //Suaviza o movimento de bater asas

		if (variacao > 2) variacao = 0;

		if (estadoDoJogo == 0) { //Condição de iniciar o jogo
			if (Gdx.input.justTouched()) {
				estadoDoJogo = 1;
			}
		} else { //Jogo rolando

			velocidadeQueda++;
			if (posicaoInicialVertical > 0 || velocidadeQueda < 0) {
				posicaoInicialVertical -= velocidadeQueda; //Passaro cai
			}

			if(estadoDoJogo == 1){
				posicaoMovimentocanoHorizontal -= deltaTime * 200; //Movimentando os canos

				if (Gdx.input.justTouched()) { // trantando o toque na tela
					velocidadeQueda = -15; //Passaro sobe
				}

				//Verifica se o cano saiu completamente da tela
				if (posicaoMovimentocanoHorizontal < -canoTopo.getWidth()) {
					posicaoMovimentocanoHorizontal = larguraDispositivo;
					alturaEntreCanosRandomica = numeroRandomico.nextInt(400) - 200;
					marcouPonto = false;
				}
				//verifica pontuação
				if (posicaoMovimentocanoHorizontal < 120) {
					if (!marcouPonto) {
						pontuacao++;
						marcouPonto = true;
					}
				}
			}
			else{//Tela game-over

				if(Gdx.input.justTouched()){ //Atualizando valores ao reiniciar o jogo
					estadoDoJogo = 0;
					pontuacao = 0;
					velocidadeQueda = 0;
					posicaoInicialVertical = alturaDispositivo/2;
					posicaoMovimentocanoHorizontal = larguraDispositivo;
				}
			}
		}

		//Configurar dados e projeção da câmera
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(fundo, 0, 0, larguraDispositivo, alturaDispositivo);
		batch.draw(canoTopo, posicaoMovimentocanoHorizontal, alturaDispositivo / 2 + espacaoEntreCanos / 2 + alturaEntreCanosRandomica);
		batch.draw(canoBaixo, posicaoMovimentocanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacaoEntreCanos / 2 + alturaEntreCanosRandomica);
		batch.draw(passaros[(int) variacao], 120, posicaoInicialVertical);
		fonte.draw(batch, String.valueOf(pontuacao), larguraDispositivo - larguraDispositivo + 40, alturaDispositivo - 50);

		if(estadoDoJogo == 2){
			batch.draw(gameOver, larguraDispositivo/2 - gameOver.getWidth()/2, alturaDispositivo/2);
			mensagem.draw(batch, "Toque para reiniciar",larguraDispositivo/2-200, alturaDispositivo/2-gameOver.getHeight()/2);
		}
		batch.end();

		passarocirculo.set(120 + passaros[0].getWidth() / 2, posicaoInicialVertical + passaros[0].getHeight() / 2, passaros[0].getWidth() / 2);

		retanguloCanoBaixo = new Rectangle(
				posicaoMovimentocanoHorizontal, alturaDispositivo / 2 - canoBaixo.getHeight() - espacaoEntreCanos / 2 + alturaEntreCanosRandomica,
				canoBaixo.getWidth(), canoBaixo.getHeight());

		retanguloCanoTopo = new Rectangle(
				posicaoMovimentocanoHorizontal, alturaDispositivo / 2 + espacaoEntreCanos / 2 + alturaEntreCanosRandomica,
				canoTopo.getWidth(), canoTopo.getHeight());

		//Desenhar formas
		/*shape.begin(ShapeRenderer.ShapeType.Filled);

		shape.circle(passarocirculo.x, passarocirculo.y, passarocirculo.radius);
		shape.rect(retanguloCanoBaixo.x, retanguloCanoBaixo.y, retanguloCanoBaixo.width, retanguloCanoBaixo.height);
		shape.rect(retanguloCanoTopo.x, retanguloCanoTopo.y, retanguloCanoTopo.width, retanguloCanoTopo.height);
		shape.setColor(Color.RED);

		shape.end();*/

		//Teste colisão
		if(Intersector.overlaps(passarocirculo, retanguloCanoBaixo) || Intersector.overlaps(passarocirculo, retanguloCanoTopo) ||
		posicaoInicialVertical <= 0 || posicaoInicialVertical >= alturaDispositivo){
			//Gdx.app.log("Colisão", "Houve colisão");
			estadoDoJogo = 2;
		}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
}
