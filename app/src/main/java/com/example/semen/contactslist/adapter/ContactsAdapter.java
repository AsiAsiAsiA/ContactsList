package com.example.semen.contactslist.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.semen.contactslist.R;
import com.example.semen.contactslist.model.Contact;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {
    private final String TAG = this.getClass().getSimpleName();

    public interface OnItemClickListener {
        void onItemClick(Contact item);
    }

    private final OnItemClickListener listener;
    private List<Contact> contacts;

    public ContactsAdapter(List<Contact> contacts,OnItemClickListener listener) {
        this.contacts = contacts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_adapter_item, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, final int position) {
        holder.bind(contacts.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public ContactsViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }

        public void bind(final Contact contact, final OnItemClickListener listener) {
            name.setText("id: " + contact.getId() + " name: " + contact.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(contact);
                }
            });
        }
    }
}
