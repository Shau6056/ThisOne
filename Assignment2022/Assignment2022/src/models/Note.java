package models;

import utils.CategoriesUtility;
import utils.Utilities;

import java.util.ArrayList;
import java.util.Objects;

//This is the class for a single Note. Notes have multiple Items.

public class Note {

    private String noteTitle = "No Title";
    private int notePriority = 1;
    private String noteCategory = "";
    private boolean isNoteArchived = false;
    private ArrayList<Item> items = new ArrayList();

    public Note(String noteTitle, int notePriority, String noteCategory) {

        if(Utilities.validateStringLength(noteTitle, 20))
        {
            this.noteTitle = noteTitle;
        }
        else
        {
            this.noteTitle = Utilities.truncateString(noteTitle, 20);
        }

        setNotePriority(notePriority);

        setNoteCategory(noteCategory);

    }

    public String getNoteTitle() {
        return noteTitle;

    }

    public void setNoteTitle(String noteTitle) {

        if(Utilities.validateStringLength(noteTitle, 20))
        {
            this.noteTitle = noteTitle;
        }

    }

    public int getNotePriority() {
        return notePriority;
    }

    public void setNotePriority(int notePriorityIn) {
        boolean notePriorityCheck = utils.Utilities.validRange(notePriorityIn, 0, 5);

        if (notePriorityCheck) {

            notePriority = notePriorityIn;
       }

    }
    public void setNoteCategory(String noteCategoryIn)
    {
        if(CategoriesUtility.isValidCategory(noteCategoryIn))
        {
            noteCategory = noteCategoryIn;
        }

    }
    public String getNoteCategory()
    {
        return noteCategory;
    }
    public boolean isNoteArchived() {

        return isNoteArchived;
    }

    public void setNoteArchived(boolean isNoteArchivedIn) {
        isNoteArchived = isNoteArchivedIn;
    }

    public ArrayList<Item> getItems() {
        return items;
    }


    public void setItems(ArrayList<Item> itemsIn)
    {
        for (Item item: itemsIn)
        {
            addItem(item);
        }
    }

    public int numberOfItems() {
        return items.size();
    }

    public boolean checkNoteCompletionStatus()
    {
        for (Item item : items) {
            while (item.isItemCompleted() == false) {
                return false;
            }
        }
        return true;
    }

    public int numberOfCompleteItems()
    {
        int total = 0;

        for (Item item: items)
        {
            if(item.isItemCompleted())
            {
                total++;
            }
        }
        return total;
    }
    public int numberOfToDoItems()
    {
        int total = 0;

        for (Item item: items)
        {
            if(!item.isItemCompleted())
            {
                total++;
            }
        }
        return total;
    }

    public String searchItemByDescription(String descriptionIn)
    {
        String result = "";
        for (Item item: items)
        {
            if(item.getItemDescription().toLowerCase().contains(descriptionIn.toLowerCase()))
            {
                result += item.toString();
            }
        }
        return result;
    }

    public boolean addItem(Item itemIn) {

        return items.add(itemIn);
    }

    public String listItems()
    {
        String listOf = "";

        if (items.isEmpty() == false)
        {
            for(int i = 0; i < items.size(); i++)
            {
                listOf += "Index number: " + i + items.get(i).toString();
            }

            return listOf;

        }
        else
        {
            return "No items added";
        }
    }

    public String listTodoItems()
    {
        String listOf = "";

            for (Item item: items)
            {
                if(!item.isItemCompleted())
                {
                    listOf = item.toString();
                }
            }
        return listOf;
        }


    public boolean isValidIndex(int numIn) {
        if (numIn >= 0 && numIn < items.size()) {
            return true;
        } else {
            return false;
        }
    }

    public Item findItem(int indexIn) {
        if (isValidIndex(indexIn)) {
            return items.get(indexIn);
        } else {
            return null;
        }
    }

    public Item deleteItem(int indexIn) {

        if (isValidIndex(indexIn)) {
            return items.remove(indexIn);
        } else {
            return null;
        }
    }


    public boolean updateItem(int index, String itemDescription, boolean itemCompleted) {

        Item searchItem = findItem(index);

        if (searchItem != null) {
            searchItem.setItemCompleted(itemCompleted);
            searchItem.setItemDescription(itemDescription);
            return true;

        } else {
            return false;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return notePriority == note.notePriority
                && isNoteArchived
                == note.isNoteArchived
                && Objects.equals(noteTitle, note.noteTitle)
                && Objects.equals(noteCategory, note.noteCategory)
                && Objects.equals(items, note.items);
    }

    public String makeItemString()
    {
        String itemString = "";

        for(int i = 0; i < items.size(); i++)
        {
            itemString += "\t" + i + ": " + items.get(i).toString();
        }

        return itemString;

    }
    public String toString() {
        return "Title = " + noteTitle +
                ", Priority = " + notePriority +
                ", Category = " + noteCategory +
                ", Archived = " + Utilities.booleanToYN(isNoteArchived) +
                "\n" + makeItemString() + "\n";
    }

}

//  return getNoteTitle() + "," + " Priority = " + getNotePriority()+", " + "Category = " + getNoteCategory() +
 //                "," + " Archived = " + Utilities.booleanToYN(isNoteArchived) +
   //             "\n" + items.toString() + "\n"; /*







