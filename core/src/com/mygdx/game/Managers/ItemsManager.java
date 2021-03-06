/**
 * Create items
 * @author HNDBP
 * @since 2018/11/27
 */
package com.mygdx.game.Managers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Components.Boomber;
import com.mygdx.game.Components.Item;
import com.mygdx.game.Maps.MapCreator;

import java.util.ArrayList;

public class ItemsManager
{
    private float ITEM_WIDTH;
    private float ITEM_HEIGHT;
    ArrayList<Item> items;

    /**
     * Constructor
     * @param map a map
     */
    public ItemsManager(MapCreator map)
    {
        ITEM_WIDTH = map.getTileWidth() * map.getUNIT_SCALE() * 3f / 4f;
        ITEM_HEIGHT = map.getTileHeight() * map.getUNIT_SCALE() * 3f / 4f;

        generateItems(map);
    }

    /**
     * Creat items
     * @param map a map
     */
    private void generateItems(MapCreator map)
    {
        items = new ArrayList<Item>();
        for (MapObject m:map.getObj_items())
        {
            float x = Float.parseFloat(m.getProperties().get("x").toString())*map.getUNIT_SCALE();
            float y = Float.parseFloat(m.getProperties().get("y").toString())*map.getUNIT_SCALE();

            items.add(new Item(m.getName(),x,y,ITEM_WIDTH, ITEM_HEIGHT));
        }
    }

    /**
     * Update map, player, time
     * @param map a map
     * @param player Bomber
     * @param dt time
     */
    public void update(MapCreator map, Boomber player,  float dt)
    {
        for (Item itm:items)
        {
            itm.update(map, player, dt);
        }
        deleteEatenItems();
    }

    /**
     * Delete item when pick it
     */
    private void deleteEatenItems()
    {

        for (int i=items.size()-1; i>=0; i--)
        {
            if (items.get(i).isEquipped())
            {
                items.remove(i);
                Music_SoundManager.getInstance().playSound("pickitem.wav");
            }
        }
    }

    /**
     * Draw item
     * @param batch to draw
     */
    public void draw(Batch batch)
    {
        for (Item itm:items)
        {
            itm.draw(batch);
        }
    }
}
