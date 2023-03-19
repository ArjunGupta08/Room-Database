# Simple Room Database

## Project Setup :



implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0"    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4"    implementation("androidx.room:room-runtime:2.5.0")
// To use Kotlin annotation processing tool (kapt)
kapt("androidx.room:room-compiler:2.5.0")    implementation("androidx.room:room-ktx:2.5.0")

## Room Database can be implemented in 5 steps -

	1) Entities (Tables)

	2) Dao

	3) Database

	4) Type-Converters

	5) Migration

You can achieve room database by just doing upper `top 3 steps` but in the manner of enhancing the database you should follow the other 2 steps as well.


`Step 1` - Create an data class and annotate it as @Entity and define a tableName =  "contact" . 

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

Here, We will define our data manipulation function like getting our saved data from Room, updating data, inserting and deleting data.



Here we use coroutines that's why we use suspend fun.

In the @Query annotation we pass :

("SELECT * FROM contact")  it means 'select all data from contact' here contact is our tableName which we declare in our data class/entity class.






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

Step 3 - Now create an abstract class named ContactDatabase and annotate it as @Database Here you need to pass two things, first one is your entity class and second is your room database version.



	- Extend your class with RoomDatabase()

	- Write a function to build your database.

	- Create an INSTANCE of your ContactDatabase

	- In if( ) condition, if the INSTANCE of your database is null than build an database OR else if your database is already created than return the INSTANCE of that database.

	- While creating your database you need to give it a name in my case i gave a name as contactDB.





@Database(entities = [Contact::class], version = 2)

abstract class ContactDatabase : RoomDatabase() {



    abstract fun contactDao(): ContactDAO



    companion object {



                private var INSTANCE: ContactDatabase? = null

    fun getDatabase(context: Context): ContactDatabase{

            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder(

                    context.applicationContext,

                    ContactDatabase::class.java,

                    "contactDB")

                    .build()

            }

            return INSTANCE!!

        }

    }

}



Here Room setUp is Completed.

Now you are all set to implement this database, initialise it in your main activity :

Write inside OnCreate method -



var database: ContactDatabase = ContactDatabase.getDatabase(this)



        database.contactDao().getContact().observe(this){

            text.text = it.toString()

            Log.d("Cheezy", it.toString())

}

      val contact = Contact (0, "Hii", "12345", Date() )



       button.setOnClickListener{



GlobalScope.launch {



            database.contactDao().insertContact(contact)



        }

    }

}

