package com.gdx.game.inventory.set;

import java.util.HashMap;

import com.gdx.game.inventory.item.InventoryItem;

public class EquipmentSet {         /** */

    private InventoryItem.ItemSetID itemSetID;
    private String itemSetDescription;
    private HashMap<String, String> bonus = new HashMap<>();
    /**
     * Standardkonstruktorn för att skapa en tom EquipmentSet.
     */
    public EquipmentSet() {
    }


    /**
     * @param equipmentSet kopierar från ett equipmentset och sätter det in på ett annat equipmentset 
     */
    public EquipmentSet(EquipmentSet equipmentSet) {    
        this.itemSetID = equipmentSet.getItemSetID();
        this.itemSetDescription = equipmentSet.getItemSetDescription();
        this.bonus = equipmentSet.getBonus();
    }


     /**
     * Hämtar det unika ID:t för utrustningssamlingen.
     * 
     * @return ID:t för utrustningssamlingen.
     */
    public InventoryItem.ItemSetID getItemSetID() {
        return itemSetID;
    }
        /**
     * Ställer in det unika ID:t för utrustningsuppsättningen.
     * @param itemSetID ItemSetID som ska tilldelas denna utrustningsuppsättning.
     */
    public void setItemSetID(InventoryItem.ItemSetID itemSetID) {
        this.itemSetID = itemSetID;
    }


    /**
     * Hämtar beskrivningen av utrustningssamlingen.
     * 
     * @return Beskrivningen av utrustningssamlingen.
     */
    public String getItemSetDescription() {
        return itemSetDescription;
    }


      /**
     * Ställer in beskrivningen av utrustningssamlingen.
     * 
     * @param itemSetDescription Beskrivningen som ska användas för denna samling.
     */
    public void setItemSetDescription(String itemSetDescription) {
        this.itemSetDescription = itemSetDescription;
    }


     /**
     * Hämtar listan över bonusar kopplade till utrustningssamlingen.
     * 
     * @return En HashMap med bonusar där nyckeln är bonustypen och värdet är bonusvärdet.
     */
    public HashMap<String, String> getBonus() {
        return bonus;
    }



     /**
     * Ställer in listan över bonusar för utrustningssamlingen.
     * 
     * @param bonus En HashMap med bonusar som ska tilldelas denna utrustningssamling.
     */
    public void setBonus(HashMap<String, String> bonus) {
        this.bonus = bonus;
    }
}
