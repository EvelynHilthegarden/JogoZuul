public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Room previousRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, meuQuarto, minhaSala, minhaCozinha;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        meuQuarto = new Room("no quarto mais legal e colorido da universidade");
        minhaSala = new Room("na sala mais confortável da universidade");
        minhaCozinha = new Room("na cozinha com comidas mais gostosas da universidade");

        // put items in the room
        outside.addItem(new Item("árvores", 0.5));
        outside.addItem(new Item("pessoas", 55));
        pub.addItem(new Item("vinho", 0.75));
        lab.addItem(new Item("computador", 30));
        lab.addItem(new Item("mouse", 0.6));
        theater.addItem(new Item("atores", 50));
        office.addItem(new Item("folhas", 0.1));
        meuQuarto.addItem(new Item("minha cama", 30));
        minhaSala.addItem(new Item("meu sofá", 50));
        minhaCozinha.addItem(new Item("meu fogão", 26));
        
        // initialise room exits
        outside.setExit("north", meuQuarto);
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", theater);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);

        meuQuarto.setExit("north", minhaSala);
        meuQuarto.setExit("south", outside);

        minhaSala.setExit("south", meuQuarto);
        minhaSala.setExit("west", minhaCozinha);

        minhaCozinha.setExit("east", minhaSala);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /*
    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("eat")) {
            eat();
        }
        else if (commandWord.equals("back")) {
            goBack(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommandList());
    }

    /**
     * Enters the specified room and prints the description.
     */
    private void enterRoom(Room nextRoom)
    {
        previousRoom = currentRoom;
        currentRoom = nextRoom;
        System.out.println(currentRoom.getLongDescription());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            enterRoom(nextRoom);
        }
    }

    /**
     * Go back to the previous room.
     */
    private void goBack(Command command){
        if(command.hasSecondWord()) {
            System.out.println("Back where?");
            return;
        }
        if (previousRoom == null) {
            System.out.println("You have nowhere to go back to!");
        }
        else {
            enterRoom(previousRoom);
        }
    }

    private void look(){
        System.out.println(currentRoom.getLongDescription());
    }

    private void eat(){
        System.out.println("Você já comeu e está de barriga cheia!");
    }

    private void back(){

    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
