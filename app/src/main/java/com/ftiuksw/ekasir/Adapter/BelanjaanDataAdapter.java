package com.ftiuksw.ekasir.Adapter;
import de.codecrafters.tableview.*;
import android.view.*;
import java.util.*;
import com.ftiuksw.ekasir.Model.*;
import android.content.*;
import android.widget.*;
import java.text.*;

public class BelanjaanDataAdapter extends TableDataAdapter
{
	public static final NumberFormat PRICE_FORMATTER = NumberFormat.getNumberInstance();
	public static long total=0;
	public BelanjaanDataAdapter(Context ctx){
		super(ctx, new ArrayList<Belanjaan>());
	}
	@Override
	public View getCellView(int row, int column, ViewGroup p3) {
		Belanjaan belanjaan = (Belanjaan) getRowData(row);
		Produk produk=belanjaan.getProduk();
		View render=null;
		switch(column){
			case 0:
				render=renderString(produk.getNama());
				break;
			case 1:
				render=renderString("Rp. "+PRICE_FORMATTER.format(produk.getHarga()));
				break;
			case 2:
				render=renderString(""+belanjaan.getQuantity());
				break;
		}
		return render;
	}
	public Belanjaan getBelBySN(String sn){
		Belanjaan pp = null;
		for(Object bb:getData()){
			Belanjaan b=(Belanjaan)bb;
			if(b.getProduk().getSn().equals(sn)){
				pp=b;
				break;
			}
		}
		return pp;
	}
	public void updateTotal(){
		long jm=0;
		for(Object bb:getData()){
			Belanjaan b=(Belanjaan)bb;
			jm=jm+(b.getProduk().getHarga()*b.getQuantity());
		}
		total=jm;
	}
	public void tambah(Produk prod, int quantity){
		Belanjaan bel=getBelBySN(prod.getSn());
		if(bel!=null){
			int prodquantity=bel.getQuantity()+1;
			if(quantity!=-1) prodquantity=quantity;
			getData().set(getData().indexOf(bel), new Belanjaan(prod, prodquantity));
		}else{
			if(quantity==-1) quantity=1;
			getData().add(new Belanjaan(prod, quantity));
		}
		updateTotal();
		notifyDataSetChanged();
	}
	public void hapus(Produk prod){
		Belanjaan bel=getBelBySN(prod.getSn());
		getData().remove(getData().indexOf(bel));
		updateTotal();
		notifyDataSetChanged();
	}
	private View renderString(final String value) {
        final TextView textView = new TextView(getContext());
        textView.setText(value);
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(14);
        return textView;
    }

}
