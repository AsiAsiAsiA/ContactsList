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

    public interface OnItemClickListener {
        void onItemClick(Contact item);
    }

    private final OnItemClickListener listener;
    private List<Contact> contacts;
    private Context context;

    public ContactsAdapter(List<Contact> contacts, OnItemClickListener listener) {
        this.contacts = contacts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_adapter_item, parent, false);
        context = parent.getContext();
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, final int position) {
        holder.bind(contacts.get(position), listener,context);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    static class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        ContactsViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contactInfo);
        }

        void bind(final Contact contact, final OnItemClickListener listener,Context context) {

            name.setText(String.format("%s %s %s %s",
                    context.getString(R.string.id),
                    contact.getId(),
                    context.getString(R.string.name),
                    contact.getName()));

            //TODO:setOnClickListener следует делать в конструкторе вьюхолдера.
            //TODO:listener-ы назначать в конструкторе viewholder'a
            //TODO: https://hackernoon.com/android-recyclerview-onitemclicklistener-getadapterposition-a-better-way-3c789baab4db
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(contact);
                }
            });
        }
    }
}
