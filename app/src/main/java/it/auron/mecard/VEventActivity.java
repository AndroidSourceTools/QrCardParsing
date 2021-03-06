/*
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2016 Rurio Luca
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package it.auron.mecard;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import net.glxn.qrgen.android.QRCode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import it.auron.library.vevent.VEvent;
import it.auron.library.vevent.VEventCostant;
import it.auron.library.vevent.VEventParser;

/**
 * Created by Luca on 29/06/2017.
 */

public class VEventActivity extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    //private Button addToCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vevent);
        setTitle("VEVENT");
        textView = (TextView) findViewById(R.id.vcard);
        imageView = (ImageView) findViewById(R.id.qrcode);
        // addToCalendar = (Button) findViewById(R.id.addtocal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String vEventString = "BEGIN:VEVENT\n" +
                "SUMMARY:Google IO\n" +
                "LOCATION:Shoreline Amphitheatre Mountain View, California\n" +
                "DTSTART:20170611T130000Z\n" +
                "DTEND:20170611T153400Z\n" +
                "END:VEVENT";

        final VEvent vEvent = VEventParser.parse(vEventString);
        vEvent.setSummary("Google I/O");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(VEventCostant.DATE_FORMAT);
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(simpleDateFormat.parse(vEvent.getDtEnd()));
            calendar.set(Calendar.DAY_OF_MONTH, 12);
            calendar.set(Calendar.HOUR_OF_DAY, 14);
            calendar.set(Calendar.MINUTE, 00);
            vEvent.setDtEnd(simpleDateFormat.format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String vEventcontent = vEvent.buildString();

        textView.setText(vEventcontent);
        imageView.setImageBitmap(QRCode.from(vEventcontent).bitmap());

      /*  addToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vEvent.addToCalendar(VEventActivity.this);
            }
        });
      */
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mecard:
                this.finish();
                return true;
            case R.id.wifiCard:
                startActivity(new Intent(VEventActivity.this, WifiActivity.class));
                this.finish();
                return true;
            case R.id.vcard:
                startActivity(new Intent(VEventActivity.this, VCardActivty.class));
                this.finish();
                return true;
            case R.id.vevent:
                return true;
            case R.id.geocard:
                startActivity(new Intent(VEventActivity.this, GeoCardActivity.class));
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
