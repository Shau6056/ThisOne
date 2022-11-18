package controllers;
import java.util.ArrayList;
import models.Item;
import models.Note;
import utils.CategoriesUtility;
import utils.Utilities;

public class NoteAPI {

    private ArrayList<Note> notes = new ArrayList<Note>();

    public boolean add(Note noteIn) {

        return notes.add(noteIn);

    }

    public boolean isValidIndex(int index) {
        return index >= 0 && index < notes.size();
    }

    public boolean updateNote(int indexToUpdate, String noteTitle, int notePriority, String noteCategory) {
        Note findNote = findNote(indexToUpdate);

        if (findNote != null) {
            findNote.setNoteTitle(noteTitle);
            findNote.setNotePriority(notePriority);
            findNote.setNoteCategory(noteCategory);
            return true;
        }
        return false;
    }

    public boolean addItemToNote(int index, String description) {
        Item itemOne = new Item(description);
        if (isValidIndex(index)) {
            notes.get(index).addItem(itemOne);
            return true;

        }

        return false;
    }

    public Note deleteNote(int indexToDelete) {
        if (isValidIndex(indexToDelete)) {
            return notes.remove(indexToDelete);
        }
        return null;
    }
    public boolean archiveNote(int indexToArchive) {
        Note noteCheck = findNote(indexToArchive);

        if (isValidIndex(indexToArchive)) {
            noteCheck.setNoteArchived(true);
            return true;
        } else {
            return false;
        }
    }

    public String archiveNotesWithAllItemsComplete() {
        String result = "";
        if (notes.isEmpty() || numberOfActiveNotes() == 0) {
            return "No active notes stored";
        } else {
            for (Note note : notes)
                if (!note.isNoteArchived() && note.checkNoteCompletionStatus()) {
                    archiveNote(notes.indexOf(note));
                    result = note.toString();
                }
        }
        return result;
    }
    public int numberOfNotes() {
        return notes.size();
    }

    public int numberOfArchivedNotes() {
        int total = 0;

        for (Note note : notes) {
            if (note.isNoteArchived()) {
                total++;
            }
        }

        return total;
    }

    public int numberOfActiveNotes() {
        int total = 0;

        for (Note note : notes) {
            if (!note.isNoteArchived()) {
                total++;
            }
        }
        return total;
    }

    public int numberOfNotesByCategory(String category) {
        int total = 0;

        for (Note note : notes) {
            if (note.getNoteCategory().equals(category)) {
                total++;

            }
        }
        return total;
    }

    public int numberOfNotesByPriority(int priority) {
        int total = 0;

        for (Note note : notes) {
            if (note.getNotePriority() == priority) {
                total++;
            }
        }
        return total;
    }

    public int numberOfItems() {
        int totalItems = 0;

        for (int i = 0; i < notes.size(); i++) {
            int getTotal = notes.get(i).numberOfItems();
            totalItems += getTotal;
        }
        return totalItems;
    }

    public String listItemStatusByCategory(String category) {

        if(notes.isEmpty())
        {
            return "There are no notes";
        }
        else
        {
            String completeItems = "";
            String todoItems = "";
            int complete= 0;
            int todo = 0;
            for (int i = 0; i < notes.size(); i++) {
                Note n = notes.get(i);
                String getNote = n.getNoteCategory();
                if (getNote.equalsIgnoreCase(category)) {
                    for (int x = 0; x < n.getItems().size(); x++) {
                        Item item = n.getItems().get(x);
                        if (item.isItemCompleted()) {
                            complete++;
                            completeItems += item.getItemDescription() + " (Note: " + n.getNoteTitle() + ")"+ "\n";
                        } else {
                            todoItems += item.getItemDescription() + " (Note: " + n.getNoteTitle() + ")"+ "\n";
                            todo++;
                        }
                    }
                }
            }

                    return "Number completed: " + complete + completeItems +"\n" +"Number todo: " + todo + "\n" + todoItems + "\n" ;

            }
        }


    public int numberOfCompleteItems() {
        int total = 0;

        for (int i = 0; i < notes.size(); i++) {

            total += notes.get(i).numberOfCompleteItems();

        }
        return total;
    }


    public int numberOfTodoItems() {
        int total = 0;

        for (int i = 0; i < notes.size(); i++) {
            total += notes.get(i).numberOfToDoItems();
        }
        return total;
    }

    public String listAllNotes() {
        String listOfNotes = "";
        String listTitle = numberOfActiveNotes() + numberOfArchivedNotes() + " active and archived note(s): " + "\n";

        if (notes.isEmpty()) {
            return "No notes stored";
        } else

            for (int i = 0; i < notes.size(); i++) {
                listOfNotes = listOfNotes + i + ":" + notes.get(i).toString();

            }

        return listTitle + "\n" + listOfNotes;
    }

    public String listActiveNotes() {
        String listAll = "";

        for (int i = 0; i < notes.size(); i++) {
            if (!notes.get(i).isNoteArchived()) {
                listAll += i + ":" + notes.get(i).toString();
            }
        }
        if (listAll.isEmpty()) {
            listAll = "No active notes stored";
        }
        return listAll;
    }


    public String listArchivedNotes() {
        String listAll = "No archived notes stored";

        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).isNoteArchived()) {
                listAll = listAll + "Index number" + i + notes.get(i).toString();
                return listAll;
            }

        }
        return listAll;
    }

    public String listNotesBySelectedCategory(String category) {

        if (notes.isEmpty()) {
            return "No notes stored";
        } else {
            String reply = numberOfNotesByCategory(category) + " notes with category " + category + "\n";
            for (int i = 0; i < notes.size(); i++)
                if (notes.get(i).getNoteCategory().equals(category)) {

                    reply += i + " : " + notes.get(i).toString() + "\n";
                }
            if (numberOfNotesByCategory(category) == 0) {
                    return "No notes with category " + category;
                }
            return reply;
        }
    }





public String listNotesBySelectedPriority(int priority) {
    if (notes.isEmpty()) {
        return "No notes stored";
    } else {
        String replyPriority = numberOfNotesByPriority(priority) + " notes with priority " + priority + "\n";
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getNotePriority() == priority)
                replyPriority += i + " : " + notes.get(i).toString() + "\n";
            }
            if (numberOfNotesByPriority(priority) == 0) {
                return "No notes found with the priority of " + priority;
            }
        else {
                return replyPriority;
            }

        }
    }

public String listTodoItems()
{
    String listAll = "";
    if(notes.isEmpty())
    {
        return "No notes stored";
    }
    for (Note note : notes) {

            listAll += note.listTodoItems();
    }
    return listAll;

    }
    public Note findNote(int index)
    {
        if(isValidIndex(index))
        {
            return notes.get(index);
        }
        return null;
    }
//not working
    public String searchNotesByTitle(String searchString) {
        {
            int total = 0;
            String result = "";
            if (notes.isEmpty()) {
                return "No notes stored";
            } else {
                for (int i = 0; i < notes.size(); i++) {
                    if (notes.get(i).getNoteTitle().toLowerCase().contains(searchString.toLowerCase())) {

                        result += notes.get(i).toString();
                        total++;
                    }
                }
                if(total == 0){
                    return "No notes contain " + searchString;
                }
            }
            return result;
        }
    }//This is the one.

        public String searchItemByDescription (String description)
        {
            if (notes.isEmpty()) {
                return "No notes stored";
            } else {
                String getItem = "Item with the description: " + description + "\n";
                for (int i = 0; i < notes.size(); i++) {
                    if (!notes.get(i).searchItemByDescription(description).isEmpty()) {
                        getItem += i + ": " + notes.get(i).searchItemByDescription(description);
                    }
                }
                return getItem;
            }
        }


    public void load(){}

    public void save(){}
}




