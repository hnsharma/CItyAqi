package com.cityaqi.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.cityaqi.R;
import com.cityaqi.database.DatabaseHelper;
import com.cityaqi.models.CityAqiData;
import com.cityaqi.models.ValueLinePoint;
import com.cityaqi.models.ValueLineSeries;
import com.cityaqi.ui.widget.ValueLineChart;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Calendar;

public class DialogUtils {

    public static BottomSheetDialog showDialogVehicleDetail(Context context, boolean cancelable,String city)
    {

        final BottomSheetDialog emailDialog = new BottomSheetDialog(context);
        emailDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = emailDialog.getWindow();
        View sheetView = ((Activity)context).getLayoutInflater().inflate(R.layout.row_item_graph, null);
        emailDialog.setContentView(sheetView);
        ValueLineChart linechartinner  = (ValueLineChart)sheetView.findViewById(R.id.linechartinner);
        TextView textView  = (TextView) sheetView.findViewById(R.id.title );
        textView.setText(city);
        sheetView.findViewById(R.id.close).setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailDialog.dismiss();
            }
        });

        loadDatainner(linechartinner,context,city,sheetView.findViewById(R.id.linechartinner));
        emailDialog.show();
        return emailDialog;
    }

    private static void loadDatainner(ValueLineChart linechartinner, Context context, String city,View message) {

        DatabaseHelper databaseHelper  = DatabaseHelper.getIntance(context);
        ArrayList<CityAqiData> cityAqiData  = databaseHelper.getCityAQiByCIty(city);
        if(cityAqiData.size() < 2)
        {
            linechartinner.setVisibility(View.GONE);
            message.setVisibility(View.VISIBLE);
            return;
        }
        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF63CBB0);
        for (int i = cityAqiData.size()-1; i >= 0; i--) {
            series.addPoint(new ValueLinePoint(TimeAgo.toDurationReal(Calendar.getInstance().getTimeInMillis()-cityAqiData.get(i).getTimeStamp(),cityAqiData.get(i).getTimeStamp()),(float) cityAqiData.get(i).getAqi()));
        }

        linechartinner.addSeries(series);
        linechartinner.startAnimation();

    }
}
