
# Spider who lives in Beata's kitchen

This is a spider who lives in Beata's kitchen and knows all her recipes. Select ingredients that you have and spider will tell you what you can make with them.

### Following SQL statements are used to create tables in MySQL database
```
CREATE TABLE recipe (id INT AUTO_INCREMENT PRIMARY KEY, url VARCHAR(400) NOT NULL,
title VARCHAR(200) NOT NULL);
CREATE TABLE ingredient (id INT AUTO_INCREMENT PRIMARY KEY, recipe_id INT NOT NULL, 
ingredient VARCHAR(2000) NOT NULL, FOREIGN KEY (recipe_id) REFERENCES recipe(id)
ON DELETE CASCADE);
```

### How it works

Spider crawls Beata's website and collects all recipes. Then it tries to recognize the ingredients by comparing against dictionary. Everything that was recognized is written to a database.


### Work in progress

Project still needs a front-end.
