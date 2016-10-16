package com.dom.tflash;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class TFlash extends ApplicationAdapter {

    private int movimento = 0;
    private int larguraDispositivo;
    private int alturaDispositivo;
    private int velocidadePulo = 0;
    private int statusDoJogo = 0;
    private int pontuacao = 0;

    private float variacao = 0;
    private float velocidadeQueda = 0;
    private float posicaoInicialVertical = 52;
    private float posicaoMovimentoCaquito;
    private float espacoEntreCaquitos;
    private float deltaTime;
    private float espacoEntreCaquitoRandomica;
    private boolean marcou;

	private SpriteBatch spriteBatch;
	private Texture[] tflashs;
    private Texture background;
    private Texture chao;
    private Texture caquito1;
    private Texture caquito2;
    private Random numeroRandomico;
    private BitmapFont fonte;
    private BitmapFont mensagem;
    private BitmapFont gameOver;
    private Circle tflashCirculo;
    private Rectangle caquitoRetangulo1;
    private Rectangle caquitoRetangulo2;
    private ShapeRenderer shapeRenderer;

	@Override
	public void create () {


        shapeRenderer = new ShapeRenderer();
        tflashCirculo = new Circle();
        caquitoRetangulo1 = new Rectangle();
        caquitoRetangulo2 = new Rectangle();
        fonte = new BitmapFont();
        fonte.setColor(Color.BLACK);
        fonte.getData().setScale(4);
        mensagem = new BitmapFont();
        mensagem.setColor(Color.BLACK);
        mensagem.getData().setScale(2);
        gameOver = new BitmapFont();
        gameOver.setColor(Color.BLACK);
        gameOver.getData().setScale(4);
        numeroRandomico = new Random();
		spriteBatch = new SpriteBatch();
        background = new Texture("backgroung.png");
        chao = new Texture("chao.png");
        tflashs = new Texture[5];
		tflashs[0] = new Texture("t_flash_run.png");
        tflashs[1] = new Texture("t_flash_run.png");
        tflashs[2] = new Texture("t_flash_runrun.png");
        tflashs[3] = new Texture("t_flash_runrun.png");
        tflashs[4] = new Texture("t_flash_jump.png");
        caquito1 = new Texture("caquito.png");
        caquito2 = new Texture("caquitomaior.png");

        larguraDispositivo = Gdx.graphics.getWidth();
        alturaDispositivo = Gdx.graphics.getHeight();

        posicaoMovimentoCaquito = larguraDispositivo;

        espacoEntreCaquitos = 400;

	}

	@Override
	public void render () {

        if(statusDoJogo == 0){
            if(Gdx.input.justTouched()){

                statusDoJogo = 1;

            }

        }else {

            if( statusDoJogo == 1){

                posicaoMovimentoCaquito -= deltaTime * 200;

                if (Gdx.input.justTouched()) {
                    velocidadeQueda = -15;

                }

                if (posicaoInicialVertical > posicaoInicialVertical || velocidadeQueda < posicaoInicialVertical)
                    posicaoInicialVertical = posicaoInicialVertical - velocidadeQueda;

                if (posicaoMovimentoCaquito < -caquito2.getWidth()) {
                    posicaoMovimentoCaquito = larguraDispositivo + 500;
                    espacoEntreCaquitoRandomica = numeroRandomico.nextInt(1000);
                }
                
            }

            deltaTime = Gdx.graphics.getDeltaTime();

            variacao += deltaTime * 8;

            velocidadeQueda++;

            velocidadePulo++;

            if (variacao > 3) variacao = 0;

            if(posicaoMovimentoCaquito < larguraDispositivo){
                if(!marcou){
                    pontuacao++;
                    marcou = true;
                }
            }else{

                if (Gdx.input.justTouched()){
                    statusDoJogo = 0;
                    pontuacao = 0;
                    posicaoInicialVertical = 52;
                    posicaoMovimentoCaquito = larguraDispositivo - 100;

                }
            }

        }

		spriteBatch.begin();

        spriteBatch.draw(background, 0, 0, larguraDispositivo, alturaDispositivo);

        spriteBatch.draw(chao, 0, 0);

        spriteBatch.draw(caquito1, posicaoMovimentoCaquito, 0);

        spriteBatch.draw(caquito2, posicaoMovimentoCaquito - espacoEntreCaquitos, 0);

		spriteBatch.draw(tflashs[(int) variacao], 150, posicaoInicialVertical);

        fonte.draw(spriteBatch, String.valueOf(pontuacao), larguraDispositivo / 2, alturaDispositivo);

        if (statusDoJogo == 2){

            gameOver.draw(spriteBatch, "Game Over!", larguraDispositivo / 2 - 130, alturaDispositivo / 2 + 50);
            mensagem.draw(spriteBatch, "Toque para reiniciar", larguraDispositivo / 2 - 200, alturaDispositivo / 2 );

        }

		spriteBatch.end();

        tflashCirculo.set(150 + tflashs[0].getHeight() / 2, posicaoInicialVertical + tflashs[0].getHeight() / 2, tflashs[0].getHeight() / 2);

        caquitoRetangulo1 = new Rectangle(
                posicaoMovimentoCaquito, 0, caquito1.getWidth(), caquito1.getHeight()
                );
        caquitoRetangulo2 = new Rectangle(
                posicaoMovimentoCaquito - espacoEntreCaquitos, 0, caquito2.getWidth(), caquito2.getHeight()
                );

        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(tflashCirculo.x, tflashCirculo.y, tflashCirculo.radius);
        shapeRenderer.rect(caquitoRetangulo1.x, caquitoRetangulo1.y, caquitoRetangulo1.width, caquitoRetangulo1.height);
        shapeRenderer.rect(caquitoRetangulo2.x, caquitoRetangulo2.y, caquitoRetangulo2.width, caquitoRetangulo2.height);
        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.end();*/

        if(Intersector.overlaps(tflashCirculo, caquitoRetangulo1) || Intersector.overlaps(tflashCirculo, caquitoRetangulo2)){

            statusDoJogo = 2;

        }

	}
	
	@Override
	public void dispose () {

	}
}
