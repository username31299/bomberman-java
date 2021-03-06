/**
 * Manage and show live and timeleft
 * @author hndbp
 * @since 2018/11/20
 */
package com.mygdx.game.Scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.BomberManGame;
import com.mygdx.game.Screens.PlayScreen;


public class Hud implements Disposable
{
    private float HEIGHT_SCREEN;
    private float WIDTH_SCREEN;
    private float SCALE;

    private final SpriteBatch batch;

    private Stage stage;
    private BitmapFont font;

    private Texture bg;
    private Sprite hudsprite;
    private Animation<Texture> anim;

    private boolean timeUp; // true if timecount == 0
    private Integer timeCount;
    private float timecountdown;
    private Label timecountLabel;
    private Label livecountLabel;
    private Image levelLabel;

    private float statetime = 0;

    private Integer liveCount;

    private int level;

    /**
     * Constructor
     * @param batch draw objects
     */
    public Hud(SpriteBatch batch, int level) {
        WIDTH_SCREEN = Gdx.graphics.getWidth();
        HEIGHT_SCREEN = Gdx.graphics.getHeight();

        this.batch = batch;
        this.level = level;

        SCALE = 55f;
        timeCount = 200;
        timecountdown = 0;
        liveCount = 3;

        bg = new Texture("core/img/hud_bg.png");

        FitViewport viewport = new FitViewport(WIDTH_SCREEN, HEIGHT_SCREEN, new OrthographicCamera());
        stage = new Stage(viewport, this.batch);
        font = new BitmapFont(Gdx.files.internal("core/font/foo.fnt"));
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);

        livecountLabel = new Label("X" + String.format("%02d", liveCount), labelStyle);
        livecountLabel.setFontScale(1f);
        livecountLabel.setPosition(3f * SCALE, 12.4f * SCALE);

        timecountLabel = new Label("X" + String.format("%03d", timeCount), labelStyle);
        timecountLabel.setFontScale(1f);
        timecountLabel.setPosition(20.7f * SCALE, 12.4f * SCALE);

        Label liveLabel = new Label("LIVES", labelStyle);
        liveLabel.setFontScale(1.2f);
        liveLabel.setPosition(2.2f * SCALE, 13.2f * SCALE);

        Label timeLabel = new Label("TIME", labelStyle);
        timeLabel.setFontScale(1.2f);
        timeLabel.setPosition(20f * SCALE, 13.2f * SCALE);

        Image liveimg = new Image(new Texture("core/img/live.png"));
        liveimg.setSize(32,30);
        liveimg.setPosition(2.3f * SCALE, 12.5f * SCALE);

        Image timeimg = new Image(new Texture("core/img/time.png"));
        timeimg.setSize(30,30);
        timeimg.setPosition(20f * SCALE, 12.5f * SCALE);

        Texture lv = new Texture("core/maps/level" + level + ".png");
        levelLabel = new Image(lv);
        levelLabel.setBounds(WIDTH_SCREEN / 3.2f, HEIGHT_SCREEN / 1.2f, lv.getWidth(), lv.getHeight());

        Array<Texture> frame = new Array<Texture>();
        for(int i = 1; i <= 26; i++)
            frame.add(new Texture("core/img/hudsprite/" + i +".png"));
        anim = new Animation<Texture>(0.66f, frame, Animation.PlayMode.LOOP);
        hudsprite = new Sprite(anim.getKeyFrame(0));
        hudsprite.setBounds( 0, HEIGHT_SCREEN / 1.23f, WIDTH_SCREEN + 1, bg.getHeight());

        stage.addActor(liveimg);
        stage.addActor(timeimg);
        stage.addActor(liveLabel);
        stage.addActor(timeLabel);
        stage.addActor(livecountLabel);
        stage.addActor(timecountLabel);
        stage.addActor(levelLabel);

    }

    /**
     * Update timeleft
     * @param dt time
     */
    public void update(float dt){
        timecountdown += dt;
        if(timecountdown >= 1){
            if (timeCount > 0) {
                timeCount--;
            }
            timecountLabel.setText("X" + String.format("%03d", timeCount));
            timecountdown = 0;
        }
    }
    /**
     * Draw objects
     * @param delta time
     */
    public void draw(float delta) {

        statetime += delta;
        hudsprite.setRegion(anim.getKeyFrame(statetime));
        batch.begin();
        hudsprite.draw(batch);
        batch.end();

        stage.draw();
    }

    /**
     * Close font, stage
     */
    @Override
    public void dispose() {
        font.dispose();
        stage.dispose();
    }

    /**
     * time left of the game
     */
    public void decreaseLiveCount()
    {
        liveCount--;
        livecountLabel.setText("X" + String.format("%02d", liveCount));
    }

    /**
     * getter
     * @return lives of bomber
     */
    public Integer getLiveCount()
    {
        return liveCount;
    }

    /**
     * getter
     * @return time
     */
    public Integer getTimeCount() { return  timeCount;}

}
