package com.example.test.xml;

import java.io.InputStream;
import java.util.List;

import com.example.test.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;

public class XmlActivity extends Activity 
{
	public void onClick_XMLToObject(View view)
	{
		try
		{
			InputStream is = getResources().openRawResource(R.raw.products);
			XML2Product xml2Product = new XML2Product();
			android.util.Xml.parse(is, Xml.Encoding.UTF_8, xml2Product);

			List<Product> products = xml2Product.getProducts();
			String msg = "The number of products is " + products.size() + "\n";
			for (Product product : products)
			{
				msg += "id : " + product.getId() + "  name : " + product.getName()
						+ "  price : " + product.getPrice() + "\n";
			}
			new AlertDialog.Builder(this).setTitle("Info").setMessage(msg)
					.setPositiveButton("ok", null).show();
		}catch (Exception e){

		}

	}
 
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xml);
	}
}