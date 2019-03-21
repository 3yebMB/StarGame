package ru.geekbrains.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.geekbrains.sprite.EnemyShip;

public class PoolManager {

    static float delayTime = 0.8f; // задержка между появлением следующего корабля
    static float delayCounter = 0.0f; // счетчик для отслеживания задержки
    static Texture enemyShipTexture; // текстура корабля
    private static final float SHIP_RESIZE_FACTOR = 0.005f;
    static List<Integer> removeIndices = new ArrayList<Integer>(); // хранит индексы кораблей, которые нужно удалить
    static Random random = new Random(); // объект класса Random для генерации случайных чисел

    public static void initialize(float width,float height,Texture enemyShipTexture){

        PoolManager.enemyShipTexture = enemyShipTexture;
        delayCounter=0.0f; // сброс счетчика задержки
        enemyShipPool.clear(); // очистка pool'а объектов
    }

    private final static Pool<EnemyShip> enemyShipPool = new Pool<EnemyShip>() {
        // этот метод запускается, когда требуется создание нового экземпляра класса enemyShip, т.е. когда pool пуст, а объект требуется

        @Override
        protected EnemyShip newObject() {
            EnemyShip enemyShip = new EnemyShip();
            // создание экземпляра спрайта корабля

            //System.out.println("Hello! I am a new one!"); // для проверки
            return enemyShip;
        }
    };

    public static EnemyShip resetShip(EnemyShip enemyShip){

        return enemyShip;
    }

    public static void cleanup(Array<EnemyShip> enemyShips){

        removeIndices.clear(); // очищаем список индексов
        for(int i=enemyShips.size-1 ; i>=0 ; i--){
//            if(!enemyShips.get(i).isAlive){
//                removeIndices.add(i); // получаем индексы объектов enemyShip, которые нам не нужны (isAlive=false)
//            }
        }

        // Удаляем объекты enemyShip из массива, согласно индексу
        for (int i = 0 ; i<removeIndices.size(); i++) {
            EnemyShip ball= enemyShips.removeIndex(i);
            enemyShipPool.free(ball);// return the ship back to the pool
        }
    }

    public static void run(Array<EnemyShip> enemyShips){
        // если счетчик delaycounter превысил значение в delayTime
        if(delayCounter>=delayTime){
            EnemyShip enemyShip= enemyShipPool.obtain(); // получаем корабль из pool'а кораблей
            resetShip(enemyShip); // переиницциализация корабля
            enemyShips.add(enemyShip); // добавляем корабль к списку
            delayCounter=0.0f;// сбросить счетчик задержки
        }
        else{
            delayCounter += Gdx.graphics.getDeltaTime();
            //в противном случае аккумулируем счетчик задержки
        }
    }

}
