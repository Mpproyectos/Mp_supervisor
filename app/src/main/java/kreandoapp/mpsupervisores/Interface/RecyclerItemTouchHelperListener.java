package kreandoapp.mpsupervisores.Interface;

import androidx.recyclerview.widget.RecyclerView;

public interface RecyclerItemTouchHelperListener {
    void onSwiped (RecyclerView.ViewHolder viewHolder,int direccion,int position);

}
