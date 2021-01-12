package app.objects.elements;

import app.frames.EndGameFrame;
import app.main.Client;
import app.main.Main;
import app.objects.characters.Monster;
import app.objects.characters.Player;
import app.properties.Config;
import app.properties.Difficulty;
import app.properties.Levels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Timer;
import java.util.*;

/**
 * This is a map
 */
public class Map extends JPanel implements KeyListener {
    Vector<Wall> walls;
    Vector<Rectangle2D> walls_graph;
    Vector<Coin> coins;
    Vector<Heart> hearts;
    Vector<Monster> monsters;
    Passage passage;
    new_Level new_level;
    Player player;

    Image FLOOR;
    Config cfg;
    Levels lvl;
    Difficulty difficulty;

    Vector<Bomb> bombs, bombs_monster;
    Rectangle2D pause_rec;
    Boolean paused, game_end;
    Double score;
    Integer level_number;
    Double screenRatioWidth, screenRatioHeight, seeking_radius, seeking_length;
    Color black_transparent;
    Font font_Lato;

    javax.swing.Timer tm_move;
    Timer tm_shot_bomb, tm_monster, tm_shot_bomb_monster;

    static HashSet<Integer> pressed = new HashSet<>();
    static double start_width, start_height;

    /**
     * Non-arg constructor
     */
    public Map() {
        lvl = Levels.getInstance();
        loadLevel();
        cfg = Config.getInstance();
        difficulty = Difficulty.getInstance();

        start_height =  Double.parseDouble(cfg.getProperty("start_height"));
        start_width = Double.parseDouble(cfg.getProperty("start_width"));
        setSize((int)start_width, (int)start_height);
        screenRatioHeight = 1.0d;
        screenRatioWidth = 1.0d;
        seeking_radius = 7.5d;
        seeking_length = (seeking_radius * 2) + 1;

        score = 0d;
        level_number = 0;
        black_transparent = new Color(0, 0, 0, 56);
        font_Lato = new Font("Lato", Font.BOLD, 60);
        game_end = false;
        paused = false;

        FLOOR = new ImageIcon("DynaBlaster_App/resources/Floor.jpg").getImage();
        bombs = new Vector<>();
        player = new Player();
        player.setStartBombs(difficulty.getBombs());
        player.setHP(difficulty.getHP());

        initializeComponents();
        addKeyListener(this);
    }

    private boolean detectWallCollision_monster(Monster monster){
        Rectangle2D monster_img = new Rectangle2D.Double(monster.getX(), monster.getY(), monster.getWidth(), monster.getHeight());
            for (Wall wall : walls) {
                Rectangle2D wall_img = new Rectangle2D.Double(wall.x, wall.y, wall.width, wall.height);
                if (wall_img.intersects(monster_img)) {
                    return true;
                }
            }
        return false;
    }

    private boolean detectWallCollision_player(){
        Rectangle2D player_img = new Rectangle2D.Double(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        for (Wall wall : walls) {
            Rectangle2D wall_img = new Rectangle2D.Double(wall.x, wall.y, wall.width, wall.height);
            if (wall_img.intersects(player_img)) {
                return true;
            }
        }
        return false;
    }

    private boolean detectWallCollision_bomb(Bomb bomb){
        Rectangle2D bomb_img = new Rectangle2D.Double(bomb.getPos_x(), bomb.getPos_y(), bomb.getWidth(), bomb.getHeight());
        if (walls != null) {
            for (Wall wall : walls) {
                Rectangle2D wall_img = new Rectangle2D.Double(wall.x, wall.y, wall.width, wall.height);
                if (wall_img.intersects(bomb_img)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean detectBombCollision_player(Bomb bomb){
        if (player != null) {
            Rectangle2D bomb_img = new Rectangle2D.Double(bomb.getPos_x(), bomb.getPos_y(), bomb.getWidth(), bomb.getHeight());
            Rectangle2D player_img = new Rectangle2D.Double(player.getX(), player.getY(), player.getWidth(), player.getHeight());
            if (bomb_img.intersects(player_img) && !bomb.getPlayers()) {
                player.damaged();
                if (player.getHP() <= 0){
                    game_end = true;
                    executeGameEnd();
                }
                return false;
            }
        }
        return true;
    }

    private boolean detectBombCollision_monster(Bomb bomb){
        Rectangle2D bomb_img = new Rectangle2D.Double(bomb.getPos_x(), bomb.getPos_y(), bomb.getWidth(), bomb.getHeight());
        if (monsters != null) {
            for (Iterator<Monster> it = monsters.iterator(); it.hasNext();){
                Monster monster = it.next();
                Rectangle2D monster_img = new Rectangle2D.Double(monster.getX(), monster.getY(), monster.getWidth(), monster.getHeight());
                if (bomb_img.intersects(monster_img) && bomb.getPlayers()) {
                    monster.setAlive(false);
                    it.remove();
                    addScore(500d);
                    return false;
                }
            }
        }
        return true;
    }

    private void detectHeartCollision(){
        Rectangle2D player_img = new Rectangle2D.Double(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        for(Iterator<Heart> it = hearts.iterator(); it.hasNext();){
            Heart heart = it.next();
            Rectangle2D heart_img = new Rectangle2D.Double(heart.x, heart.y, heart.width, heart.height);
            if (heart_img.intersects(player_img)) {
                player.addHP();
                addScore(25d);
                int index = hearts.indexOf(heart);
                int id = -1;
                for (String line : Levels.configuration) {
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == 'h') {
                            ++id;
                            if (id == index) {
                                updateLevelConfiguration(line, i);
                                it.remove();
                                break;
                            }
                        }
                    }
                }
            }
        }
        repaint();
    }

    private void detectCoinCollision(){
        Rectangle2D player_img = new Rectangle2D.Double(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        for(Iterator<Coin> it = coins.iterator(); it.hasNext();){
            Coin coin = it.next();
            Rectangle2D coin_img = new Rectangle2D.Double(coin.x, coin.y, coin.width, coin.height);
            if (coin_img.intersects(player_img)) {
                addScore(100d);
                int index = coins.indexOf(coin);
                int id = -1;
                for (String line : Levels.configuration) {
                    for (int i = 0; i < line.length(); i++) {
                        if (line.charAt(i) == 'c') {
                            ++id;
                            if (id == index) {
                                updateLevelConfiguration(line, i);
                                it.remove();
                                break;
                            }
                        }
                    }
                }
            }
        }
        repaint();
    }

    private void detectNewLevelCollision(){
        if (new_level != null){
            Rectangle2D player_img = new Rectangle2D.Double(player.getX(), player.getY(), player.getWidth(), player.getHeight());
            Rectangle2D new_Level_img = new Rectangle2D.Double(new_level.x, new_level.y, new_level.width, new_level.height);
            if (new_Level_img.intersects(player_img)) {
                for (String line : Levels.configuration){
                    for (int i = 0; i < line.length(); i++){
                        if (line.charAt(i) == 'n'){
                            line = line.substring(0, i) + '1' + line.substring(i+1);
                        }
                    }
                }
                new_level = null;
                changeMap();
            }
        }
    }

    private boolean detectPlayer_monster(Monster monster) {
        Rectangle2D player_img = new Rectangle2D.Double(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        Ellipse2D searching_area = new Ellipse2D.Double((int)monster.getX() - (int)(seeking_radius * monster.getWidth()),
                (int)monster.getY() - (int)(seeking_radius * monster.getHeight()),
                seeking_length * (int)monster.getWidth(), seeking_length * (int)monster.getHeight());
        return searching_area.intersects(player_img);
    }

    private void addScore(Double addScore){
        score += addScore;
    }

    private void createBombTimer(){
        tm_shot_bomb = new Timer();
        tm_shot_bomb.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (bombs) {
                    for (Iterator<Bomb> it = bombs.iterator(); it.hasNext(); ) {
                        Bomb bomb = it.next();
                        if ((bomb.getPos_x() + bomb.getWidth()) * screenRatioWidth < getWidth() &&
                                (bomb.getPos_y() + bomb.getHeight()) * screenRatioHeight < getHeight() &&
                                bomb.getPos_x() * screenRatioWidth > 0 && bomb.getPos_y() * screenRatioHeight > 0 &&
                                detectWallCollision_bomb(bomb) && detectBombCollision_player(bomb) &&
                                detectBombCollision_monster(bomb)) {
                            bomb.move();
                        } else {
                            it.remove();
                        }
                        repaint();
                    }
                }
            }
        }, 0, Integer.parseInt(cfg.getProperty("bomb_time")));
    }

    private void createBombTimer_monster() {
        tm_shot_bomb_monster = new Timer();
        tm_shot_bomb_monster.schedule(new TimerTask() {
            @Override
            public void run() {
                if (bombs_monster != null) {
                    synchronized (bombs_monster) {
                        for (Iterator<Bomb> it = bombs_monster.iterator(); it.hasNext(); ) {
                            Bomb bomb = it.next();
                            if ((bomb.getPos_x() + bomb.getWidth()) * screenRatioWidth < getWidth() &&
                                    (bomb.getPos_y() + bomb.getHeight()) * screenRatioHeight < getHeight() &&
                                    bomb.getPos_x() * screenRatioWidth > 0 && bomb.getPos_y() * screenRatioHeight > 0 &&
                                    detectWallCollision_bomb(bomb) && detectBombCollision_player(bomb) &&
                                    detectBombCollision_monster(bomb)) {
                                bomb.move();
                            } else {
                                it.remove();
                            }
                            repaint();
                        }
                    }
                }
            }
        }, 0, Integer.parseInt(cfg.getProperty("bomb_time")));
    }

    private void createMonsterTimer(){
        tm_monster = new Timer();
        tm_monster.schedule(new TimerTask() {
            @Override
            public void run() {
                if (monsters != null) {
                    synchronized (monsters) {
                        for (Monster monster : monsters) {
                            if (monster.getDirection() == KeyEvent.VK_DOWN) {
                                if (detectWallCollision_monster(monster) ||
                                        !((monster.getY() + monster.getHeight()) * screenRatioHeight <= getHeight())) {
                                    monster.move(KeyEvent.VK_UP);
                                } else {
                                    monster.move(KeyEvent.VK_DOWN);
                                }
                            } else if (monster.getDirection() == KeyEvent.VK_UP) {
                                if (detectWallCollision_monster(monster) ||
                                        !(monster.getY() * screenRatioHeight >= 0)) {
                                    monster.move(KeyEvent.VK_DOWN);
                                } else {
                                    monster.move(KeyEvent.VK_UP);
                                }
                            } else if (monster.getDirection() == KeyEvent.VK_LEFT) {
                                if (detectWallCollision_monster(monster) ||
                                        !(monster.getX() * screenRatioWidth >= 0)) {
                                    monster.move(KeyEvent.VK_RIGHT);
                                } else {
                                    monster.move(KeyEvent.VK_LEFT);
                                }
                            } else if (monster.getDirection() == KeyEvent.VK_RIGHT) {
                                if (detectWallCollision_monster(monster) ||
                                        !((monster.getX() + monster.getWidth()) * screenRatioWidth <= getWidth())) {
                                    monster.move(KeyEvent.VK_LEFT);
                                } else {
                                    monster.move(KeyEvent.VK_RIGHT);
                                }
                            }
                            repaint();
                        }
                    }
                }
            }
        }, 0, (Integer.parseInt(cfg.getProperty("bomb_time"))));

        synchronized (monsters) {
            for (Monster monster : monsters){
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (bombs_monster != null){
                            synchronized (bombs_monster) {
                                if (detectPlayer_monster(monster)) {
                                    for (int i = KeyEvent.VK_LEFT; i <= KeyEvent.VK_DOWN; i++) {
                                        bombs_monster.addElement(new Bomb(monster.getX(), monster.getY(), i,
                                                (int) monster.getWidth() / 5, false));
                                    }
                                    synchronized (tm_shot_bomb_monster) {
                                        tm_shot_bomb_monster.cancel();
                                        tm_shot_bomb_monster.purge();
                                        createBombTimer_monster();
                                    }
                                }
                                if (!monster.getAlive()) {
                                    this.cancel();
                                }
                            }
                        }
                    }
                }, 0, 1000);
            }
        }
    }

    private void createPlayerTimer(){
        tm_move = new javax.swing.Timer(Integer.parseInt(cfg.getProperty("move_time")), e -> {
            if (pressed.contains(KeyEvent.VK_DOWN)) {
                if (detectWallCollision_player() ||
                        (player.getY() + player.getHeight()) * screenRatioHeight == getHeight()) {
                    pressed.remove(KeyEvent.VK_DOWN);
                    player.move(KeyEvent.VK_UP);
                } else {
                    player.move(KeyEvent.VK_DOWN);
                }
            } else if (pressed.contains(KeyEvent.VK_UP)) {
                if(detectWallCollision_player() ||
                player.getY() * screenRatioHeight == 0){
                    pressed.remove(KeyEvent.VK_UP);
                    player.move(KeyEvent.VK_DOWN);
                } else {
                    player.move(KeyEvent.VK_UP);
                }
            } else if (pressed.contains(KeyEvent.VK_LEFT)) {
                if(detectWallCollision_player() ||
                player.getX() * screenRatioWidth == 0){
                    pressed.remove(KeyEvent.VK_LEFT);
                    player.move(KeyEvent.VK_RIGHT);
                } else {
                    player.move(KeyEvent.VK_LEFT);
                }
            } else if (pressed.contains(KeyEvent.VK_RIGHT)) {
                if(detectWallCollision_player() ||
                        (player.getX() + player.getWidth()) * screenRatioWidth == getWidth()){
                    pressed.remove(KeyEvent.VK_RIGHT);
                    player.move(KeyEvent.VK_LEFT);
                } else {
                    player.move(KeyEvent.VK_RIGHT);
                }
            }
            detectHeartCollision();
            detectCoinCollision();
            detectNewLevelCollision();
            repaint();
        });
        tm_move.start();
    }

    private void stopAllTimers(){
        tm_move.stop();
        tm_shot_bomb.cancel();
        tm_shot_bomb.purge();
        tm_monster.cancel();
        tm_monster.purge();
        tm_shot_bomb_monster.cancel();
        tm_shot_bomb_monster.purge();
    }

    private void paintPlayer(Graphics2D g2d) {
        if (player != null){
            player.paint(g2d,
                    player.getX() * screenRatioWidth,
                    player.getY() * screenRatioHeight,
                    player.getWidth() * screenRatioWidth,
                    player.getHeight() * screenRatioHeight);
        }
    }

    private void paintMonster(Graphics2D g2d) {
        if (monsters != null && monsters.size() != 0) {
            synchronized (monsters) {
                for (Monster monster : monsters) {
                    monster.paint(g2d,
                            monster.getX() * screenRatioWidth,
                            monster.getY() * screenRatioHeight,
                            monster.getWidth() * screenRatioWidth,
                            monster.getHeight() * screenRatioHeight);
                }
            }
        }
    }

    private void paintBombs(Graphics2D g2d) {
        if (bombs != null && bombs.size() != 0){
            int x, y;
            synchronized (bombs) {
                for(Bomb bomb: bombs){
                    x = (int)(bomb.getPos_x() * screenRatioWidth);
                    y = (int)(bomb.getPos_y() * screenRatioHeight);
                    bomb.setAttributes(Math.round((float)this.getWidth() / (3 * Levels.configuration.elementAt(0).length())),
                            Math.round((float)this.getHeight() / (3 * Levels.configuration.size())));
                    bomb.paint(g2d, x, y);
                }
            }
        }

        if (bombs_monster != null && bombs_monster.size() != 0) {
            int x, y;
            synchronized (bombs_monster) {
                try {
                    for(Iterator<Bomb> it = bombs_monster.iterator(); it.hasNext();){
                        Bomb bomb = it.next();
                        x = (int)(bomb.getPos_x() * screenRatioWidth);
                        y = (int)(bomb.getPos_y() * screenRatioHeight);
                        bomb.setAttributes(Math.round((float)this.getWidth() / (3 * Levels.configuration.elementAt(0).length())),
                                Math.round((float)this.getHeight() / (3 * Levels.configuration.size())));
                        bomb.paint(g2d, x, y);
                    }
                } catch (ConcurrentModificationException e) {
                    System.out.println("ConcurrentModificationException");
                }
            }
        }
    }

    private void paintMapObjects(Graphics2D g2d) {
        int o=0;
        int walls_counter = 0, hearts_counter = 0, coins_counter = 0;
        g2d.clearRect(0,0,getWidth(),getHeight());

        for(String line: Levels.configuration) {
            for (int i = 0; i < line.length(); i++) {
                g2d.drawImage(FLOOR,
                        (int)Math.round(i * ((double)getWidth() / line.length())),
                        (int)Math.round(o * ((double)getHeight() / Levels.configuration.size())),
                        (int)Math.round((double)getWidth() / line.length()),
                        (int)Math.round((double)getHeight() / Levels.configuration.size()),
                        null);
                g2d.setColor(black_transparent);
                g2d.fill(new Rectangle2D.Double(
                        (int)Math.round(i * ((double)getWidth() / line.length())),
                        (int)Math.round(o * ((double)getHeight() / Levels.configuration.size())),
                        (int)Math.round((double)getWidth() / line.length()),
                        (int)Math.round((double)getHeight() / Levels.configuration.size())));

                if (line.charAt(i) == '0') {
                    if (walls != null){
                        walls.elementAt(walls_counter).paint(g2d,
                                walls.elementAt(walls_counter).x * screenRatioWidth,
                                walls.elementAt(walls_counter).y * screenRatioHeight,
                                walls.elementAt(walls_counter).width * screenRatioWidth,
                                walls.elementAt(walls_counter).height * screenRatioHeight);
                        ++walls_counter;
                    }
                } else if (line.charAt(i) == 'p') {
                    if (passage != null){
                        passage.paint(g2d,
                                passage.x * screenRatioWidth,
                                passage.y * screenRatioHeight,
                                passage.width * screenRatioWidth,
                                passage.height * screenRatioHeight);
                    }
                }
                else if (line.charAt(i) == 'c') {
                    if (coins != null){
                        if (coins.iterator().hasNext()){
                            coins.elementAt(coins_counter).paint(g2d,
                                    coins.elementAt(coins_counter).x * screenRatioWidth,
                                    coins.elementAt(coins_counter).y * screenRatioHeight,
                                    coins.elementAt(coins_counter).width * screenRatioWidth,
                                    coins.elementAt(coins_counter).height * screenRatioHeight);
                            ++coins_counter;
                        }
                    }
                } else if (line.charAt(i) == 'h') {
                    if (hearts != null){
                        if (hearts.iterator().hasNext()){
                            hearts.elementAt(hearts_counter).paint(g2d,
                                    hearts.elementAt(hearts_counter).x * screenRatioWidth,
                                    hearts.elementAt(hearts_counter).y * screenRatioHeight,
                                    hearts.elementAt(hearts_counter).width * screenRatioWidth,
                                    hearts.elementAt(hearts_counter).height * screenRatioHeight);
                            ++hearts_counter;
                        }
                    }
                } else if (line.charAt(i) == 'n') {
                    if (new_level != null){
                        new_level.paint(g2d,
                                new_level.x * screenRatioWidth,
                                new_level.y * screenRatioHeight,
                                new_level.width * screenRatioWidth,
                                new_level.height * screenRatioHeight);
                    }
                }
            }
            ++o;
        }
    }

    private void paintStrings(Graphics2D g2d) {
        if (player != null){
            AffineTransform at = new AffineTransform(getWidth()/start_width,0.0f,0.0f,
                    getHeight()/start_height,0.0f,0.0f);
            String str_score = "Your score: " + Math.round(score);
            String str_HP_bombs = "HP: " + player.getHP() + "  Bombs: " + player.getHasBombs();
            String str_nick = "Nick: " + Main.nick;
            Font font_Strings = font_Lato.deriveFont(20f);
            font_Strings = font_Strings.deriveFont(at);
            FontMetrics metrics = g2d.getFontMetrics(font_Strings);
            g2d.setColor(Color.BLACK);
            g2d.setFont(font_Strings);
            g2d.drawString(str_nick, (getWidth() - metrics.stringWidth(str_nick)) / 2f, 20f * screenRatioHeight.floatValue());
            g2d.drawString(str_HP_bombs, (getWidth() - metrics.stringWidth(str_HP_bombs)) / 2f, 40f * screenRatioHeight.floatValue());
            g2d.drawString(str_score, (getWidth() - metrics.stringWidth(str_score)) / 2f, 59f * screenRatioHeight.floatValue());
        }
    }

    private void paintPause(Graphics2D g2d) {
        if(pause_rec != null) {
            AffineTransform at = new AffineTransform(getWidth()/start_width,0.0f,0.0f,
                    getHeight()/start_height,0.0f,0.0f);
            Font font_Pause = font_Lato.deriveFont(60f);
            font_Pause = font_Pause.deriveFont(at);
            FontMetrics metrics = g2d.getFontMetrics(font_Pause);
            String str_paused = "Game is paused!";
            g2d.setColor(black_transparent);
            g2d.fill(pause_rec);
            g2d.setColor(Color.white);
            g2d.setFont(font_Pause);
            g2d.drawString(str_paused, (getWidth() - metrics.stringWidth(str_paused)) / 2,
                    (getHeight() - metrics.getHeight()) / 2 + metrics.getAscent());
        }
    }

    private void paintEndGame(Graphics2D g2d) {
        g2d.clearRect(0,0, getWidth(), getHeight());
        String str_end;
        if (score <= 0) {
            str_end = "You lost!";
        }
        else {
            str_end = "The end!";
        }
        Font font = font_Lato.deriveFont(60f);
        FontMetrics metrics = g2d.getFontMetrics(font);
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(str_end, (getWidth() - metrics.stringWidth(str_end)) / 2,
                (getHeight() - metrics.getHeight()) / 4 + metrics.getAscent());
    }

    private void updateLevelConfiguration(String line, int i){
        int temp = Levels.configuration.indexOf(line);
        try{
            Levels.configuration.set(temp, Levels.configuration.elementAt(temp).substring(0, i) + '1'
                    + Levels.configuration.elementAt(temp).substring(i + 1));
        } catch (Exception e) {
            System.out.println("Exception thrown in updating level configuration");
        }
    }

    private void changeMap(){
        ++level_number;
        addScore(1000d);
        if (level_number < Config.Scenario.size()){
            deleteComponents();
            loadLevel();
            initializeComponents();

            if (Main.difficulty_level == 1){
                player.setHasBombs(player.getHasBombs() + player.getStartBombs());
            }
            player.setX(0);
            player.setY(0);
        } else {
            executeGameEnd();
        }
    }

    private void loadLevel() {
        if (!Client.isOffline) {
            lvl.loadFromServer();
        } else {
            lvl.load();
        }
    }

    private void executeGameEnd() {
        game_end = true;
        paused = true;
        repaint();
        addScore(player.getHP() * 25d);
        deleteComponents();
        player = null;
        bombs = null;
        EndGameFrame endGameFrame = new EndGameFrame((int)Math.round(score), Main.nick);
        if (Client.isOffline) {
            endGameFrame.saveToFile();
        } else {
            endGameFrame.saveToServer();
        }
        endGameFrame.open();
    }

    private void setPlayerStartAttributes(){
        player.setX(0);
        player.setY(0);
        player.setWidth((double)getWidth() / (2 * Levels.configuration.elementAt(0).length()));
        player.setHeight((double)getHeight() / (2 * Levels.configuration.size()));
        player.setHP(difficulty.getHP());
        player.setStartBombs(difficulty.getBombs());
    }

    private void deleteComponents(){
        walls = null;
        walls_graph = null;
        coins = null;
        hearts = null;
        monsters = null;
        bombs_monster = null;
        stopAllTimers();
    }

    private void initializeComponents(){
        walls = new Vector<>();
        walls_graph = new Vector<>();
        coins = new Vector<>();
        hearts = new Vector<>();
        monsters = new Vector<>();
        bombs_monster = new Vector<>();

        int o = 0;
        int monster_counter = 0, wall_counter = 0, coin_counter = 0, heart_counter = 0;
        for (String line : Levels.configuration) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '0') {
                    walls.addElement(new Wall());
                    walls.elementAt(wall_counter).x = i * (start_width / line.length());
                    walls.elementAt(wall_counter).y = o * (start_height / Levels.configuration.size());
                    walls.elementAt(wall_counter).width = start_width / line.length();
                    walls.elementAt(wall_counter).height = start_height / Levels.configuration.size();
                    ++wall_counter;
                } else if (line.charAt(i) == 'p') {
                    passage = new Passage();
                    passage.x = i * (start_width / line.length());
                    passage.y = o * (start_height / Levels.configuration.size());
                    passage.width = start_width / line.length();
                    passage.height = start_height / Levels.configuration.size();
                } else if (line.charAt(i) == 'c') {
                    coins.addElement(new Coin());
                    coins.elementAt(coin_counter).x = (i + 0.25d) * (start_width / line.length());
                    coins.elementAt(coin_counter).y = (o + 0.25d) * (start_height / Levels.configuration.size());
                    coins.elementAt(coin_counter).width = 0.5d * start_width / line.length();
                    coins.elementAt(coin_counter).height = 0.5d * start_height / Levels.configuration.size();
                    ++coin_counter;
                } else if (line.charAt(i) == 'h') {
                    hearts.addElement(new Heart());
                    hearts.elementAt(heart_counter).x = (i + 0.25d) * (start_width / line.length());
                    hearts.elementAt(heart_counter).y = (o + 0.25d) * (start_height / Levels.configuration.size());
                    hearts.elementAt(heart_counter).width = 0.5d * start_width / line.length();
                    hearts.elementAt(heart_counter).height = 0.5d * start_height / Levels.configuration.size();
                    ++heart_counter;
                } else if (line.charAt(i) == 'n') {
                    new_level = new new_Level();
                    new_level.x = i * (start_width / line.length());
                    new_level.y = o * (start_height / Levels.configuration.size());
                    new_level.width = start_width / line.length();
                    new_level.height = start_height / Levels.configuration.size();
                } else if (line.charAt(i) == 'm') {
                    monsters.addElement(new Monster());
                    monsters.elementAt(monster_counter).setX((i + 0.25d) * (start_width / line.length()));
                    monsters.elementAt(monster_counter).setY((o + 0.25d) * (start_height / Levels.configuration.size()));
                    monsters.elementAt(monster_counter).setWidth(0.5d * start_width / line.length());
                    monsters.elementAt(monster_counter).setHeight(0.5d * start_height / Levels.configuration.size());
                    updateLevelConfiguration(line, i);
                    ++monster_counter;
                }
            }
            ++o;
        }

        setPlayerStartAttributes();
        tm_shot_bomb = new Timer();
        tm_shot_bomb_monster = new Timer();
        createPlayerTimer();
        createMonsterTimer();
    }

    /**
     * Method which determines what is painted and how
     * @param g graphic object used in the paint method
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Dimension sz = getSize();
        Graphics2D g2d = (Graphics2D) g;
        screenRatioWidth = (sz.width / start_width);
        screenRatioHeight = (sz.height / start_height);

        paintMapObjects(g2d);
        paintBombs(g2d);
        paintMonster(g2d);
        paintPlayer(g2d);
        paintStrings(g2d);
        paintPause(g2d);
        if (game_end){
            paintEndGame(g2d);
        }
    }

    /**
     * Method vulnerable on key type
     * @param e KeyEvent
     */
    @Override
    public void keyTyped(KeyEvent e) { }

    /**
     * Method vulnerable on key press, in this code it is responsible for controls
     * @param e KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_ESCAPE){
            if(!paused) {
                paused = true;
                pause_rec = new Rectangle2D.Float(0, 0, getWidth(), getHeight());
                stopAllTimers();
                repaint();
            }
            else {
                paused = false;
                pause_rec = null;

                tm_move.start();
                createBombTimer();
                createMonsterTimer();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE && player.getHasBombs() > 0) {
            if (!paused) {
                player.shoot();
                bombs.addElement(new Bomb(player.getX(), player.getY(), player.getDirection(),
                        (int)player.getWidth() / 5, true));

                tm_shot_bomb.cancel();
                tm_shot_bomb.purge();
                createBombTimer();
            }
        }
        pressed.add(e.getKeyCode());
    }

    /**
     * Method vulnerable on key release
     * @param e KeyEvent
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if(pressed != null){
            pressed.remove(e.getKeyCode());
        }
    }
}
