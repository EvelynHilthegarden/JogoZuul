import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    private HashSet items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        items = new HashSet();
    }

    // Retorna uma descrição longa do quarto, além das saídas que o mesmo possui
    public String getLongDescription(){
        return "You are " + description + ".\n" + getItemString() + "\n" + getItemWeight() + "\n" + getExitString();
    }

    //Retona a descrição dos itens presentes nos quartos
    private String getItemString()
    {
        String returnDescription = "Os itens do local e seus respectivos pesos são:";
        for(Iterator iter = items.iterator(); iter.hasNext();) {
            returnDescription += " \n" + ((Item) iter.next()).getItemDescription();
        }
        return returnDescription;
    }

    //Retorna o peso dos itens presentes nos quartos
    private String getItemWeight()
    {
        String returnWeight = "";
        for(Iterator iter = items.iterator(); iter.hasNext();) {
            returnWeight += " \n" + ((Item) iter.next()).getItemWeight();
        }
        return returnWeight;
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     */
    public void setExit(String direction, Room neighboor)
    {
        exits.put(direction, neighboor);
    }

    public Room getExit(String direction){
        return exits.get(direction);
    }

    public String getExitString()
    {
        String returnString = "As saídas são:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    public void addItem(Item item)
    {
        items.add(item);
    }

}
