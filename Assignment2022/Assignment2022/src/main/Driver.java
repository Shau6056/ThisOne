package main;

import controllers.NoteAPI;
import models.Item;
import models.Note;
import utils.ScannerInput;
import utils.Utilities;

import java.util.Scanner;

public class Driver {

    private NoteAPI noteAPI = new NoteAPI();

    public static void main(String [] args)
    {
        new Driver();
    }

    public Driver()
    {
        runMenu();
    }

    private int mainMenu()
    {
        return ScannerInput.readNextInt("""
    
            ------------------------------------------------------------------
            |                       NOTE KEEPER APP                          |
            ------------------------------------------------------------------
            |NOTE MENU                                                       |
            |   1) Add a note                                                |
            |   2) List all notes (all, active, archived)                    |
            |   3) Update a note                                             |
            |   4) Delete a note                                             |
            |   5) Archive a note                                            |
            ------------------------------------------------------------------
            |ITEM MENU                                                       |
            |   6) Add an item to a note                                     |
            |   7) Update item description on a note                         |
            |   8) Delete an item from a note                                |
            |   9) Mark item as complete/todo                                |
            ------------------------------------------------------------------
            |REPORT MENU FOR NOTES
            |   10) All notes and their items active & archived)             |
            |   11) Archive notes whose items are all complete               |
            |   12) All notes within a selected Category                     |
            |   13) All notes within a selected Priority                     |
            |   14) Search for all notes (by note title)                      |
            ------------------------------------------------------------------
            |REPORT MENU FOR ITEMS                                           |
            |   15) All items that are todo (with note title)                |
            |   16) Overall number of items todo/complete                    |
            |   17) Todo/complete items by specific Category                 |
            |   18) Search for all items (by item description)               |
            ------------------------------------------------------------------
            |SETTINGS MENU                                                   |
            |   20) Save                                                     |
            |   21) Load                                                     |
            |   0)  Exit                                                     |
            ------------------------------------------------------------------
            ==>>  """);
    
    }

    private void runMenu()
    {
        int option = mainMenu();

        while(option != 0)
        {
            switch(option)
            {
                case 1 -> addNote();
                case 2 -> viewNotes();
                case 3 -> updateNote();
                case 4 -> deleteNote();
                case 5 -> archiveNote();
                case 6 -> addItemToNote();
                case 10 -> printAllNotes();
                default -> System.out.println("Not a valid option " + option);

            }
            ScannerInput.readNextLine("\nPress any key to continue...");

            option = mainMenu();


        }
    }

    private void addNote() {
        String noteTitle = ScannerInput.readNextLine("Enter the Note Title: ");
        int notePriority = ScannerInput.readNextInt("Enter the Note Priority: ");
        String noteCategory = ScannerInput.readNextLine("Enter the note Category: ");

        boolean isAdded = noteAPI.add(new Note(noteTitle, notePriority, noteCategory));

        if (isAdded) {
            System.out.println("The note has been successfully added!");
        } else {
            System.out.println("There has been an error, the note could NOT be added.");
        }

    }
    private void viewNotes()
    {
        if(noteAPI.numberOfNotes() == 0)
        {
            System.out.println("There are no notes stored in the system.");
        }
        else {
            int option = ScannerInput.readNextInt("""
                    --------------------------------
                    |   1) View ALL notes          |
                    |   2) View ACTIVE notes       |
                    |   3) View ARCHIVED notes     |
                    --------------------------------
                    ==>>  """);

            if (option == 1) {
                System.out.println(noteAPI.listAllNotes());
            }
            if (option == 2) {
                System.out.println(noteAPI.listActiveNotes());
            }
            if (option == 3) {
                System.out.println(noteAPI.listArchivedNotes());
            }
        }
    }

    private void listAllNotes()
    {
        if(noteAPI.numberOfNotes()==0)
        {
            System.out.println("There are no notes in the system.");
        }
        else {
            System.out.println(noteAPI.listAllNotes());
        }
        }


    private void updateNote() {
        listAllNotes();

        if (noteAPI.numberOfNotes() > 0) {
            int indexToUpdate = ScannerInput.readNextInt("Enter the index of the note you would like to update  ");

            if (noteAPI.isValidIndex(indexToUpdate)) {
                String noteTitle = ScannerInput.readNextLine("Enter the note Title ");
                int notePriority = ScannerInput.readNextInt("Enter the note priorty");
                String noteCategory = ScannerInput.readNextLine("Enter the note category ");

                if (noteAPI.updateNote(indexToUpdate, noteTitle, notePriority, noteCategory)) {
                    System.out.println("Update Successful");
                } else if (noteAPI.updateNote(indexToUpdate,noteTitle, notePriority, noteCategory) == false) {
                    System.out.println("Update was NOT successful");
                }
            else
                {
                    System.out.println("There was no notes for this index");

                }
            }
        }
    }

    private void deleteNote()
    {
        if(noteAPI.numberOfNotes()<0)
        {
            noteAPI.listAllNotes();

            int deleteNoteIndex = ScannerInput.readNextInt("What note would you like to delete? ");

            if(noteAPI.isValidIndex(deleteNoteIndex))
            {
                noteAPI.deleteNote(deleteNoteIndex);
                System.out.println("Your note is deleted");
            }
            else if(noteAPI.isValidIndex(deleteNoteIndex) == false)
            {
                System.out.println("Your note index could not be found");
            }
            else
            {
                System.out.println("We could NOT delete this note");
            }
        }
    }

    private void archiveNote()
    {
        System.out.println(noteAPI.listActiveNotes());

        if(noteAPI.numberOfActiveNotes() > 0)
        {
            int index = ScannerInput.readNextInt("What active note do you want to archive? ");
            boolean check = noteAPI.archiveNote(index);

            if(check == true)
            {
                System.out.println("Note was SUCCESSFULLY archived");
            }
            else
            {
                System.out.println("The not was NOT archived");
            }
        }
    }

    public void addItemToNote()
    {
        System.out.println(noteAPI.listActiveNotes());

        int index = ScannerInput.readNextInt("Select index of active note: ");

       if(noteAPI.isValidIndex(index))
        {
            String description = ScannerInput.readNextLine("What is the item description: ");

            boolean didWork = noteAPI.addItemToNote(index, description);

            if(didWork == true)
            {
                System.out.println("Item added successfully");
            }
            else
            {
                System.out.println("Unsuccessful");
            }

        }
    }
    public void updateItemDescInNote() {// ask about this if you have a chance.
        System.out.println(noteAPI.listActiveNotes());

        int index = ScannerInput.readNextInt("Enter the index of an active note: ");

        if (noteAPI.isValidIndex(index)) {

        }
    }

    public void deleteItemFromNote()
    {

    }

    public void printAllNotes()
    {
        System.out.println("------------------------------------------------------------------" + "\n" + noteAPI.numberOfActiveNotes() + " active note(s): "
                + "\n" + noteAPI.listActiveNotes() + "------------------------------------------------------------------" + "\n" + noteAPI.numberOfArchivedNotes() +" archived note(s): " + "\n"
                + noteAPI.listArchivedNotes() + "\n" + "------------------------------------------------------------------");
    }

    public void archiveNotesWithAllItemsComplete()
    {
        noteAPI.archiveNotesWithAllItemsComplete();
    }

    public void printArchivedNotes()
    {
        System.out.println(noteAPI.listArchivedNotes());
    }

    public void printActiveNotes()
    {
        System.out.println(noteAPI.listActiveNotes());
    }

    public void printActiveAndArchivedReport()
    {
        System.out.println("------------------------------------------------------------------" +
                "\n" + " Report for active and archived notes" + "\n" + noteAPI.numberOfActiveNotes() + " active note(s): "
                + "\n" + noteAPI.listActiveNotes() + "------------------------------------------------------------------" + "\n" + noteAPI.numberOfArchivedNotes() +" archived note(s): " + "\n"
                + noteAPI.listArchivedNotes() + "\n" + "------------------------------------------------------------------");
    }

    public void printNotesBySelectedCategory()
    {
        String category = ScannerInput.readNextLine("What note category would you like to see? ");

        System.out.println(noteAPI.listNotesBySelectedCategory(category));

    }

    public void printNotesByPriority()
    {
        int priority = ScannerInput.readNextInt("What note priority do you want to search? (1 - 5) ");

        System.out.println(noteAPI.listNotesBySelectedPriority(priority));
    }

    public void searchNotesByTitle()
    {
        String title = ScannerInput.readNextLine("What note title do you want to search for? ");

        System.out.println(noteAPI.searchNotesByTitle(title));
    }

    public void printAllTodoItems()
    {
        System.out.println(noteAPI.listTodoItems());
    }







    }


