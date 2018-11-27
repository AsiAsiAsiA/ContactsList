package com.example.semen.contactslist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.semen.contactslist.R;
import com.example.semen.contactslist.model.Contact;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private ItemClickListener itemClickListener;
    private final List<Contact> contacts;

    //Устанавлиет новый список и обновляет recyclerview
    public void setContacts(List<Contact> contacts) {
        this.contacts.clear();
        this.contacts.addAll(contacts);
        notifyDataSetChanged();
    }

    private final Context context;

    public ContactsAdapter(Context context, List<Contact> contacts) {
        this.contacts = contacts;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_adapter_item, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, final int position) {
        holder.bind(contacts.get(position), context);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener{
        void onClick(String id);
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView name;

        ContactsViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contactInfo);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        void bind(final Contact contact,  Context context) {
            name.setText(String.format("%s %s %s %s",
                    context.getString(R.string.id),
                    contact.getId(),
                    context.getString(R.string.name),
                    contact.getName()));
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            itemClickListener.onClick(contacts.get(position).getId());
        }
    }
}
