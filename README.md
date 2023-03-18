# Simple Room Database

## Room Database can be implemented in 5 steps -

	1) Entities (Tables)

	2) Dao

	3) Database

	4) Type-Converters

	5) Migration

You can achieve room database by just doing upper `top 3 steps` but in the manner of enhancing the database you should follow the other 2 steps as well.


`Step 1` - Create an data class and annotate it as @Entity and define a tableName =  "xyz" . 

Note - We will use this `table name` in next step.

data class example -
    
    @Entity(tableName = "contact")
    data class Contact(    

        @PrimaryKey(autoGenerate = true)  
        val id:Long,    

        val name:String,    

        val phone:String,    
  
        val createdDate:Date,   
    )


`Step 2` - Create an interface class and annotate it as @Dao

Example -

    @Dao
    interface ContactDAO {
    
       @Insert
       suspend fun insertContact(contact: Contact)
 
       @Update
       suspend fun updateContact(contact:Contact) 
       
       @Delete
       suspend fun deleteContact(contact: Contact) 
       
       @Query("SELECT * FROM contact")
       fun getContact() : LiveData<List<Contact>>
    }

