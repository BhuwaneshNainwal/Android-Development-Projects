 /**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.CoffeeOrderingApp;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.CoffeeOrderingApp.R;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    String message = "";
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        //Small pop message
        if(quantity == 0){
            Context context = getApplicationContext();
            CharSequence text = "Please , select at least one quantity";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        //Checking name field
        EditText nameText = (EditText) findViewById(R.id.Name);
        String name = "";
        name = nameText.getText().toString();
        if(name.matches("")){
            Context context = getApplicationContext();
            CharSequence text = "Name field cannot be empty";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        int price = 50 * quantity;
        String personName = getNameText();

        //Extras
        String toppingCream = "";
        String toppingChocolate = "";
        boolean isCheckedCream = ((CheckBox) findViewById(R.id.cream)).isChecked();
        boolean isCheckedChocolate = ((CheckBox) findViewById(R.id.chocolate)).isChecked();

        if(isCheckedCream) {
            toppingCream = "Whipped Cream added\n";
            price += 10;
        }
        if(isCheckedChocolate) {
            toppingChocolate = "Chocolate Cream added\n";
            price += 20;
        }
        message = "Name : " +  personName + "\n" + "Quantity : "+ quantity + "\n" +  toppingCream + toppingChocolate + "Price : " + price + " rs\n" + "Thank You! \n";
        displayMessage(message);
    }

    public String getNameText(){
        EditText editText = findViewById(R.id.Name);
        String personName = editText.getText().toString();
        return personName;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    public void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public void sendEmail(View view)
    {
        String subject = "coffee ordered";
        composeEmail(subject , message);
    }


    public void composeEmail(String subject , String msg) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, msg);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void increment(View view){
        quantity++;
        display(quantity);
    }

    public void decrement(View view){
        if(quantity > 0)
            quantity--;
        display(quantity);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView order_Summary_TextView = (TextView) findViewById(R.id.order_summary_text_view);
        order_Summary_TextView.setText(message);
    }
}
