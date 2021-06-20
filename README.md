# BookshelfBoxes
1.17 Spigot plugin which allows bookshelves to be opened like chests. They can only be used to store books, maps, and banner patterns. 

Stores all bookshelf content data based on their coordinates, in the file BlocksInv.data. Deleting this file effectively wipes all bookshelf information.

Prevents opening or breakage of bookshelves which are currently opened by another user, in order to prevent duping.

The contents of bookshelves which are broken or moved by the environment (a piston, tnt, creeper, what have you) will be inaccessible until a new bookshelf is places at the same coordinates as the bookshelf was originally located. 
