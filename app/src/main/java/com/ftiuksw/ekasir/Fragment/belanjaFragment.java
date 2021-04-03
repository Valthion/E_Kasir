package com.ftiuksw.ekasir.Fragment;
import android.support.v4.app.*;
import android.os.*;
import android.view.*;
import com.ftiuksw.ekasir.*;
import com.ftiuksw.ekasir.Adapter.*;

import android.support.design.widget.*;
import android.widget.*;
import com.ftiuksw.ekasir.Widget.*;
import de.codecrafters.tableview.listeners.*;
import com.ftiuksw.ekasir.Model.*;

public class belanjaFragment extends Fragment
{
	public static TextView totaljum;
	public static BottomSheetBehavior bottomSheetBehavior;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.belanja, container, false);
		tabelBelanjaan velanjaan=(tabelBelanjaan) v.findViewById(R.id.belanjaan);
		velanjaan.setDataAdapter(MainActivity.dataBalanjaan);
		velanjaan.addDataClickListener(new DtaClickListener());
		velanjaan.addDataLongClickListener(new DataLongClickListener());
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		FloatingActionButton fabshop=(FloatingActionButton) view.findViewById(R.id.fab_shopping);
		totaljum=(TextView) view.findViewById(R.id.totaljumlah);
		bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottomSheet));
		
		fabshop.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					new inputProdukScanner(p1.getContext()).shoping();
				}
			});
		
		if(BelanjaanDataAdapter.total!=0){
			totaljum.setText("Rp. "+BelanjaanDataAdapter.PRICE_FORMATTER.format(BelanjaanDataAdapter.total));
			bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
		}else{
			bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
		}
	}
	private class DtaClickListener implements TableDataClickListener<Belanjaan> {
		@Override
		public void onDataClicked(int p1, Belanjaan belanjaan) {
			new EditorDialog(getActivity(), belanjaan, totaljum);
		}
    }
	private class DataLongClickListener implements TableDataLongClickListener<Belanjaan> {
        @Override
        public boolean onDataLongClicked(int rowIndex, Belanjaan belanjaan) {
			return true;
        }
    }
	
}
