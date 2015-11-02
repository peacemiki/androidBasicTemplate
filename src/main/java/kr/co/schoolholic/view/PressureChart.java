package kr.co.schoolholic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class PressureChart extends View {

	public enum NormalRange {
		Male,
		Female
	};

	int mNormalRangeMin = 50, mNormalRangeMax = 100;

	Path mXYAxis;
	Path mXAxisDivisionLine;
	Path mYAxisDivisionLine;
	
	Paint mXYAxisPaint = new Paint();
	Paint mXYAxisDivisionPaint = new Paint();
	Paint mXYLabelPaint = new Paint();

	Paint mPressureStrokePaint = new Paint();
	Paint mHighPressurePaint = new Paint();
	Paint mLowPressurePaint = new Paint();
	
	int mYMax = 220;
	int mYMin = 40;
	int mYValueInterval = 20;
	
	float mXPadding;
	float mYPadding;
	float mXLineLength;
	float mYLineLength;
	
	float mXYAxisLineWidth = 2.0f;
	float mXYAxisDivisionLineWidth = 1.0f;
	
	float mLabelFontSize = 18.0f;
	float mCircleRadius = 6.0f;
	
	String[] mXLabels = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
	
	//Pressure values
	int[] mHighPressureValues = {90, 0, 100, 130, 120, 150, 140};
	int[] mLowPressureValues = {70, 75, 80, 110, 100, 130, 120};

	public PressureChart(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void setCircleRadius(float radius) {
		mCircleRadius = radius;
	}
	
	public void setXLabels(String[] values) {
		mXLabels = values;
	}
	
	public void setHighPressureValues(int[] values) {
		mHighPressureValues = values;
	}
	
	public void setLowPressureValues(int[] values) {
		mLowPressureValues = values;
	}

	public void setNormalRange(NormalRange range) {
		if( range == NormalRange.Female ) {
			mNormalRangeMin = 100;
			mNormalRangeMax = 200;
		}
		else {
			mNormalRangeMin = 50;
			mNormalRangeMax = 100;
		}
	}
	
	public void redraw() {
		initialize(getWidth(), getHeight());
		invalidate();
	}
	
	//size assigned to view
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		initialize(getWidth(), getHeight());
	}
	
	private void initialize(int width, int height) {
//		Log.e("DEBUG", "width = " + this.getWidth());
		mXPadding = (int)(width * 0.1);
		mYPadding = (int)(height * 0.1);
		mXLineLength = (int)(width * 0.8); 
		mYLineLength = (int)(height * 0.8);
		mXYAxis = new Path();
		mXAxisDivisionLine = new Path();
		mYAxisDivisionLine = new Path();
		
		//XY Axis
		mXYAxis.moveTo(mXPadding, mYPadding);
		mXYAxis.lineTo(mXPadding, mYPadding + mYLineLength);
		mXYAxis.lineTo(mXPadding + mXLineLength, mYPadding + mYLineLength);
		mXYAxisPaint.setStrokeWidth(mXYAxisLineWidth);
		mXYAxisPaint.setColor(Color.WHITE);
		mXYAxisPaint.setStyle(Style.STROKE);
		
		
		//Y Axis Division line
		int count = (mYMax-mYMin)/mYValueInterval;
		float yInterval = mYLineLength / count;
		for(int i=0; i<count; i++) {
			mXAxisDivisionLine.moveTo(mXPadding + mXYAxisDivisionLineWidth, mYPadding + yInterval * i);
			mXAxisDivisionLine.lineTo(mXPadding + mXLineLength, mYPadding + yInterval * i);
		}

		//X Axis Division line
		float xInterval = mXLineLength / mXLabels.length;
//		Log.e("DEBUG", "xInterval = " + xInterval);
		for(int i=1; i<=mXLabels.length; i++) {
			mYAxisDivisionLine.moveTo(mXPadding + xInterval * i - xInterval/2 + mXYAxisLineWidth, mYPadding);
			mYAxisDivisionLine.lineTo(mXPadding + xInterval * i - xInterval/2 + mXYAxisLineWidth, mYPadding + mYLineLength);
		}
		
		mXYAxisDivisionPaint.setStrokeWidth(mXYAxisDivisionLineWidth);
		mXYAxisDivisionPaint.setColor(Color.rgb(0x88, 0x88, 0x88));
		mXYAxisDivisionPaint.setStyle(Style.STROKE);
		
		//XY Axis Label paint
		mXYLabelPaint.setColor(Color.WHITE);
		mXYLabelPaint.setTextSize(mLabelFontSize);
		
		//Pressure value paint
		mPressureStrokePaint.setColor(Color.WHITE);
		mPressureStrokePaint.setStyle(Style.STROKE);
		mPressureStrokePaint.setStrokeWidth(6.0f);
		mLowPressurePaint.setColor(Color.rgb(0x21, 0x80, 0xed));
		mLowPressurePaint.setStyle(Style.FILL);
		mHighPressurePaint.setColor(Color.rgb(0xe4, 0x04, 0x81));
		mHighPressurePaint.setStyle(Style.FILL);
	}

	@Override
	protected void onDraw(Canvas canvas) {
//		canvas.drawColor(Color.BLACK);
		
		canvas.drawPath(mXYAxis, mXYAxisPaint);
		canvas.drawPath(mXAxisDivisionLine, mXYAxisDivisionPaint);
		canvas.drawPath(mYAxisDivisionLine, mXYAxisDivisionPaint);
		
		drawLabels(canvas);
		drawValues(canvas, mHighPressureValues);
		fillPoints(canvas);

		super.onDraw(canvas);
	}

	private void drawValues(Canvas canvas, int[] values) {
		int count = (mYMax-mYMin)/mYValueInterval;
		float yInterval = mYLineLength / count;
		float pixelPerPoint = yInterval / mYValueInterval;
		float xInterval = mXLineLength / (float)values.length;
		float xPadding = mXPadding + (mXLineLength / mXLabels.length)/2;

		float xPoints[] = new float[values.length];
		float yPoints[] = new float[values.length];

//		Log.e("DEBUG", "yInterval = " + yInterval + ", 20 = " + 20*pixelPerPoint);

		//calculate pressure x, y point.
		float x, y;
		for(int i=0; i<values.length; i++) {
			x = xPadding + xInterval*i + mCircleRadius/2;
			y = (mYPadding + mYLineLength) - ((values[i] - mYMin) * pixelPerPoint) - mXYAxisLineWidth;
			xPoints[i] = x;
			yPoints[i] = y;
		}


		//draw line
		Path valueLine = new Path();
		boolean isFirst = true;
		for(int i=0; i<values.length; i++) {
			if(values[i] < mYMin || values[i] > mYMax) continue;

			if(isFirst) {
				valueLine.moveTo(xPoints[i], yPoints[i]);
				isFirst = false;
			}
			else {
				valueLine.lineTo(xPoints[i], yPoints[i]);
			}
		}
		canvas.drawPath(valueLine, mXYAxisPaint);

//		fillPoints(lowXPoints, lowYPoints, highXPoints, highYPoints, canvas);

		//draw pressure circle
		for(int i=0; i<values.length; i++) {
			if(values[i] < mYMin || values[i] > mYMax) continue;

			canvas.drawCircle(xPoints[i], yPoints[i], mCircleRadius, mLowPressurePaint);
			canvas.drawCircle(xPoints[i], yPoints[i], mCircleRadius, mXYAxisPaint);
		}
	}
	
	private void drawValues(Canvas canvas) {
		int count = (mYMax-mYMin)/mYValueInterval;
		float yInterval = mYLineLength / count;
		float pixelPerPoint = yInterval / mYValueInterval;
		float xInterval = mXLineLength / (float)mHighPressureValues.length;
		float xPadding = mXPadding + (mXLineLength / mXLabels.length)/2;
		
		float lowXPoints[] = new float[mLowPressureValues.length];
		float lowYPoints[] = new float[mLowPressureValues.length];
		float highXPoints[] = new float[mHighPressureValues.length];
		float highYPoints[] = new float[mHighPressureValues.length];

//		Log.e("DEBUG", "yInterval = " + yInterval + ", 20 = " + 20*pixelPerPoint);
		
		//calculate pressure x, y point.
		float x, y;
		for(int i=0; i<mHighPressureValues.length; i++) {
//			x = xPadding + xInterval*i + mCircleRadius/2 - (mCircleRadius*3/7)*i;
			x = xPadding + xInterval*i + mCircleRadius/2;
			y = (mYPadding + mYLineLength) - ((mHighPressureValues[i] - mYMin) * pixelPerPoint) - mXYAxisLineWidth;
			highXPoints[i] = x;
			highYPoints[i] = y;
			
//			x = xPadding + xInterval*i + mCircleRadius/2 - (mCircleRadius*3/7)*i;
			x = xPadding + xInterval*i + mCircleRadius/2;
			y = (mYPadding + mYLineLength) - ((mLowPressureValues[i] - mYMin) * pixelPerPoint) - mXYAxisLineWidth;
			lowXPoints[i] = x;
			lowYPoints[i] = y;
		}
		

		//draw pressure line
		Path highPressureLine = new Path();
		Path lowPressureLine = new Path();
		boolean isFirst = true;
		for(int i=0; i<mHighPressureValues.length; i++) {
			if(mHighPressureValues[i] < mYMin || mHighPressureValues[i] > mYMax) continue;
			if(mLowPressureValues[i] < mYMin || mLowPressureValues[i] > mYMax) continue;
			
			if(isFirst) {
				highPressureLine.moveTo(highXPoints[i], highYPoints[i]);
				lowPressureLine.moveTo(lowXPoints[i], lowYPoints[i]);
				isFirst = false;
			}
			else {
				highPressureLine.lineTo(highXPoints[i], highYPoints[i]);
				lowPressureLine.lineTo(lowXPoints[i], lowYPoints[i]);
			}
		}
		canvas.drawPath(highPressureLine, mXYAxisPaint);
		canvas.drawPath(lowPressureLine, mXYAxisPaint);

//		fillPoints(lowXPoints, lowYPoints, highXPoints, highYPoints, canvas);
		
		//draw pressure circle
		for(int i=0; i<mHighPressureValues.length; i++) {
			if(mHighPressureValues[i] < mYMin || mHighPressureValues[i] > mYMax) continue;
			if(mLowPressureValues[i] < mYMin || mLowPressureValues[i] > mYMax) continue;

			canvas.drawCircle(highXPoints[i], highYPoints[i], mCircleRadius, mHighPressurePaint);
			canvas.drawCircle(highXPoints[i], highYPoints[i], mCircleRadius, mXYAxisPaint);
			
			canvas.drawCircle(lowXPoints[i], lowYPoints[i], mCircleRadius, mLowPressurePaint);
			canvas.drawCircle(lowXPoints[i], lowYPoints[i], mCircleRadius, mXYAxisPaint);
		}
	}
	
	private void drawLabels(Canvas canvas) {
		//Y Label
		int count = (mYMax-mYMin)/mYValueInterval;
		float yInterval = mYLineLength / count;
		for(int i=0; i<=count; i++) {
			String text = String.valueOf(mYMax - mYValueInterval * i);
			float width = mXYLabelPaint.measureText(text);
			canvas.drawText(text, mXPadding - width - 10, mYPadding+yInterval*i + mLabelFontSize/2, mXYLabelPaint);
		}
		
		//X Label
		float xInterval = mXLineLength / mXLabels.length;
		for(int i=0; i<mXLabels.length; i++) {
			String text = mXLabels[i];
			float width = mXYLabelPaint.measureText(text);
			canvas.drawText(text, mXPadding + xInterval*i + xInterval/2 - width/2 + mXYAxisLineWidth, mYPadding+mYLineLength + mLabelFontSize, mXYLabelPaint);
		}
	}

	private void fillPoints(Canvas canvas) {
		int count = (mYMax-mYMin)/mYValueInterval;
		float yInterval = mYLineLength / count;
		float pixelPerPoint = yInterval / mYValueInterval;
		float minY = (mYPadding + mYLineLength) - ((mNormalRangeMin - mYMin) * pixelPerPoint);
		float maxY = (mYPadding + mYLineLength) - ((mNormalRangeMax - mYMin) * pixelPerPoint);

		Path rangePath = new Path();
		Paint rangePaint = new Paint();
		rangePaint.setColor(Color.argb(84, 0xff, 0xff, 0xff));;
		rangePaint.setStyle(Style.FILL);

		rangePath.moveTo(mXPadding, minY);
		rangePath.lineTo(mXPadding, maxY);
		rangePath.lineTo(mXLineLength + mXPadding, maxY);
		rangePath.lineTo(mXLineLength + mXPadding, minY);

		canvas.drawPath(rangePath, rangePaint);
	}
}
