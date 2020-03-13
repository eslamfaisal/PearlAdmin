package com.pearladmin.android.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pearladmin.android.R;
import com.pearladmin.android.models.Investment;
import com.pearladmin.android.models.InvestmentBox;
import com.pearladmin.android.models.MyMarkerView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class BoxViewHolder extends RecyclerView.ViewHolder implements
        OnChartValueSelectedListener {

    private static final String TAG = "LineChartActivity1";
    private Context context;
    private LineChart chart;
    private TextView name, solid_count;
    private ProgressBar progressBar;
    private float solidCount = 0f;

    public BoxViewHolder(@NonNull View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        chart = itemView.findViewById(R.id.chart1);
        name = itemView.findViewById(R.id.name);
        progressBar = itemView.findViewById(R.id.mf_progress_bar);
        solid_count = itemView.findViewById(R.id.solid_count);

    }

    public void setHolderData(DocumentSnapshot snapshot) {

        InvestmentBox investmentBox = snapshot.toObject(InvestmentBox.class);
        name.setText(investmentBox.getName());

        setupChart();

        FirebaseFirestore.getInstance().collection("boxes")
                .document(investmentBox.getId()).collection("investments")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    for (DocumentChange change : queryDocumentSnapshots.getDocumentChanges()) {
                        Investment investment = change.getDocument().toObject(Investment.class);
                        solidCount += (float) investment.getShares_count();
                        addEntry((float) investment.getShares_count());
                        solid_count.setText(solidCount + "% Sold");
                        progressBar.setProgress((int) solidCount);
                        if (solidCount>=100){

                        }
                    }

                });

    }

    private void setupChart() {

        {   // // Chart Style // //


            // background color
            chart.setBackgroundColor(Color.WHITE);

            // disable description text
            chart.getDescription().setEnabled(false);

            // enable touch gestures
            chart.setTouchEnabled(true);

            // set listeners
            chart.setOnChartValueSelectedListener(this);
            chart.setDrawGridBackground(false);

            // create marker to display box when values are selected
            MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view);

            // Set the marker to the chart
            mv.setChartView(chart);
//            chart.setMarker(mv);

            // enable scaling and dragging
            chart.setDragEnabled(false);
            chart.setScaleEnabled(false);
            // chart.setScaleXEnabled(true);
            // chart.setScaleYEnabled(true);

            // force pinch zoom along both axis
            chart.setPinchZoom(false);
        }

        XAxis xAxis;
        {   // // X-Axis Style // //
            xAxis = chart.getXAxis();

            // vertical grid lines
            xAxis.enableGridDashedLine(10f, 10f, 0f);
        }

        YAxis yAxis;
        {   // // Y-Axis Style // //
            yAxis = chart.getAxisLeft();

            // disable dual axis (only use LEFT axis)
            chart.getAxisRight().setEnabled(false);

            // horizontal grid lines
            yAxis.enableGridDashedLine(10f, 10f, 0f);

            // axis range
//            yAxis.setAxisMaximum(20);
            yAxis.setAxisMinimum(0f);
        }

//
//        {   // // Create Limit Lines // //
//            LimitLine llXAxis = new LimitLine(9f, "Index 10");
//            llXAxis.setLineWidth(4f);
//            llXAxis.enableDashedLine(10f, 10f, 0f);
//            llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//            llXAxis.setTextSize(10f);
////            llXAxis.setTypeface(tfRegular);
//
//            LimitLine ll1 = new LimitLine(150f, "Upper Limit");
//            ll1.setLineWidth(4f);
//            ll1.enableDashedLine(10f, 10f, 0f);
//            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
//            ll1.setTextSize(10f);
////            ll1.setTypeface(tfRegular);
//
//            LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
//            ll2.setLineWidth(4f);
//            ll2.enableDashedLine(10f, 10f, 0f);
//            ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
//            ll2.setTextSize(10f);
////            ll2.setTypeface(tfRegular);
//
//            // draw limit lines behind data instead of on top
//            yAxis.setDrawLimitLinesBehindData(true);
//            xAxis.setDrawLimitLinesBehindData(true);
//
//            // add limit lines
//            yAxis.addLimitLine(ll1);
//            yAxis.addLimitLine(ll2);
//            //xAxis.addLimitLine(llXAxis);
//        }

        setData();

        // draw points over time
        chart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // draw legend entries as lines
        l.setForm(Legend.LegendForm.LINE);

//        addEntry( (float) (Math.random() * 180) - 30);

    }

    private void addEntry(float f) {
        LineData lineData = chart.getData();
        if (lineData != null) {
            ILineDataSet iLineDataSet = lineData.getDataSetByIndex(0);
            if (iLineDataSet == null) {
                iLineDataSet = createSet();
                lineData.addDataSet(iLineDataSet);
            }
            lineData.addEntry(new Entry((float) iLineDataSet.getEntryCount(), f), 0);
            lineData.notifyDataChanged();
            this.chart.notifyDataSetChanged();
            this.chart.setVisibleXRangeMaximum(20.0f);
            this.chart.moveViewToX((float) lineData.getEntryCount());
        }
    }

    private LineDataSet createSet() {
        LineDataSet lineDataSet = new LineDataSet(null, "dynamic_data");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setColor(ColorTemplate.getHoloBlue());
        lineDataSet.setLineWidth(2.0f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setFillAlpha(65);
        lineDataSet.setFillColor(ColorTemplate.getHoloBlue());
        lineDataSet.setHighLightColor(Color.rgb(244, 117, 117));
        lineDataSet.setValueTextColor(-1);
        lineDataSet.setValueTextSize(9.0f);
        lineDataSet.setDrawValues(false);
        return lineDataSet;
    }

    private void setData() {

        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(0, 0));
        LineDataSet set1;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, " ");

            set1.setDrawIcons(false);

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f);

            // black lines and points
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);

            // line thickness and point size
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);

            // draw points as solid circles
            set1.setDrawCircleHole(false);

            // customize legend entry
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // text size of values
            set1.setValueTextSize(9f);

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f);

            // set the filled area
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // set color of filled area
            if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the data sets

            // create a data object with the data sets
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
        }


    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
