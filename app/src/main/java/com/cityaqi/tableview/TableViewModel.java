/*
 * MIT License
 *
 * Copyright (c) 2021 Evren Coşkun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.cityaqi.tableview;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;


import com.cityaqi.R;
import com.cityaqi.models.CityAqiData;
import com.cityaqi.tableview.model.Cell;
import com.cityaqi.tableview.model.ColumnHeader;
import com.cityaqi.tableview.model.RowHeader;
import com.cityaqi.utils.TimeAgo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by evrencoskun on 4.02.2018.
 */

public class TableViewModel {

    // Columns indexes
    public static final int MOOD_COLUMN_INDEX = 3;
    public static final int GENDER_COLUMN_INDEX = 4;

    // Constant values for icons
    public static final int SAD = 1;
    public static final int HAPPY = 2;
    public static final int BOY = 1;
    public static final int GIRL = 2;

    // Constant size for dummy data sets
    private static final int COLUMN_SIZE = 500;
    private static final int ROW_SIZE = 500;

    // Drawables
    @DrawableRes
    private final int mBoyDrawable;
    @DrawableRes
    private final int mGirlDrawable;
    @DrawableRes
    private final int mHappyDrawable;
    @DrawableRes
    private final int mSadDrawable;

    public TableViewModel() {
        // initialize drawables
        mBoyDrawable = R.drawable.ic_launcher_background;
        mGirlDrawable = R.drawable.ic_launcher_background;
        mHappyDrawable = R.drawable.ic_launcher_background;
        mSadDrawable = R.drawable.ic_launcher_background;
    }

    @NonNull
    private List<RowHeader> getSimpleRowHeaderList(ArrayList<CityAqiData> cityAqiData) {
        List<RowHeader> list = new ArrayList<>();
        for (int i = 0; i < cityAqiData.size(); i++) {
            RowHeader header = new RowHeader(String.valueOf(i), cityAqiData.get(i).getCity());
            list.add(header);
        }

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    @NonNull
    private List<ColumnHeader> getRandomColumnHeaderList() {
        List<ColumnHeader> list = new ArrayList<>();

            ColumnHeader header = new ColumnHeader(String.valueOf(0), "Currect AQI");
            list.add(header);
        ColumnHeader header1 = new ColumnHeader(String.valueOf(1), "Last update");
        list.add(header1);

        return list;
    }

    /**
     * This is a dummy model list test some cases.
     */
    @NonNull
    private List<List<Cell>> getCellListForSortingTest(ArrayList<CityAqiData> cityAqiData) {
        List<List<Cell>> list = new ArrayList<>();
        for (int i = 0; i < cityAqiData.size(); i++) {
            List<Cell> cellList = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                Cell cell;
                Object text = "cell " + j + " " + i;
                String id = j + "-" + i;
                if (j == 0) {
                    text = cityAqiData.get(i).getAqi()+"";
                    cell = new Cell(id, text);
                    cell.setAqi(cityAqiData.get(i).getAqi());
                    cell.setAQI(true);

                } else  {
                    text = TimeAgo.toDuration(Calendar.getInstance().getTimeInMillis()-cityAqiData.get(i).getTimeStamp(),cityAqiData.get(i).getTimeStamp());
                    cell = new Cell(id, text);
                }





                cellList.add(cell);
            }
            list.add(cellList);
        }

        return list;
    }

    @DrawableRes
    public int getDrawable(int value, boolean isGender) {
        if (isGender) {
            return value == BOY ? mBoyDrawable : mGirlDrawable;
        } else {
            return value == SAD ? mSadDrawable : mHappyDrawable;
        }
    }

    @NonNull
    public List<List<Cell>> getCellList(ArrayList<CityAqiData> cityAqiData) {
        return getCellListForSortingTest(cityAqiData);
    }

    @NonNull
    public List<RowHeader> getRowHeaderList(ArrayList<CityAqiData> cityAqiData) {
        return getSimpleRowHeaderList(cityAqiData);
    }

    @NonNull
    public List<ColumnHeader> getColumnHeaderList() {
        return getRandomColumnHeaderList();
    }
}
