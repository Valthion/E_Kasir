package com.ftiuksw.ekasir.Fragment;

import android.os.*;

import com.ftiuksw.ekasir.Adapter.ProdukDataAdapter;
import com.ftiuksw.ekasir.Model.Produk;

import de.codecrafters.tableview.listeners.*;
import android.widget.*;
import android.view.*;
import android.support.v4.app.Fragment;

import com.ftiuksw.ekasir.MainActivity;
import android.support.design.widget.*;
import com.kennyc.bottomsheet.*;
import com.ftiuksw.ekasir.R;
import com.ftiuksw.ekasir.*;
import com.ftiuksw.ekasir.Widget.*;

public class productFragment extends Fragment 
{
	FloatingActionButton fab_addbtn;
	CoordinatorLayout mycoor;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.myproduct, container, false);
		fab_addbtn=(FloatingActionButton) v.findViewById(R.id.fab_addproduct);
		TableGue tableView = (TableGue) v.findViewById(R.id.tableView);
		
		tableView.setDataAdapter(MainActivity.dataproduk);
		tableView.addDataClickListener(new DataClickListener());
		tableView.addDataLongClickListener(new DataLongClickListener());
		fab_addbtn.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					new BottomSheet.Builder(getActivity())
						.setSheet(R.menu.inputmodemenu)
						.setListener(new BottomSheetListener(){

							@Override
							public void onSheetShown(BottomSheet p1) {
								// TODO: Implement this method
							}

							@Override
							public void onSheetItemSelected(BottomSheet p1, MenuItem p2) {
								switch(p2.getItemId()){
									case R.id.tambahsatuan:
										new ProdukDialog(getActivity(), null);
										break;
									case R.id.tambahbanyak:
										new inputProdukScanner(getActivity()).tambahkanProduk();
										break;
								}
							}

							@Override
							public void onSheetDismissed(BottomSheet p1, int p2) {
								// TODO: Implement this method
							}
						}).show();
				}
			});
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mycoor=(CoordinatorLayout) view.findViewById(R.id.myproductCoordinatorLayout1);
	}
	
    
	private class DataClickListener implements TableDataClickListener<Produk> {
        @Override
        public void onDataClicked(int rowIndex, final Produk clickedData) {
			new BottomSheet.Builder(getActivity())
				.setSheet(R.menu.popupmenu)
				.setTitle(clickedData.getNama()+" - (Rp. "+ProdukDataAdapter.PRICE_FORMATTER.format( clickedData.getHarga())+")")
				.setListener(new BottomSheetListener(){
					@Override
					public void onSheetShown(BottomSheet p1) {
						// TODO: Implement this method
					}
					@Override
					public void onSheetItemSelected(BottomSheet p1, MenuItem menu) {
						switch(menu.getItemId()){
							case R.id.addtoproduct:
								MainActivity.dataBalanjaan.tambah(clickedData, -1);
								break;
							case R.id.menuedit:
								new ProdukDialog(getActivity(), clickedData);
								break;
							case R.id.menudelete:
								Snackbar.make(mycoor, "Tekan Hapus untuk mengkonfirmasi", Snackbar.LENGTH_LONG)
									.setAction("Hapus", new View.OnClickListener(){
										@Override
										public void onClick(View p1) {
											MainActivity.dataproduk.hapus(clickedData);
											Toast.makeText(getActivity(), "Terhapus", Toast.LENGTH_SHORT).show();
										}
									}).show();
								break;
						}
					}
					@Override
					public void onSheetDismissed(BottomSheet p1, int p2) {
						// TODO: Implement this method
					}
				})
				.show();
        }
    }
	private class DataLongClickListener implements TableDataLongClickListener<Produk> {
        @Override
        public boolean onDataLongClicked(int rowIndex, Produk clickedData) {
            new ProdukDialog(getActivity(), clickedData);
			return true;
        }
    }
	
}
