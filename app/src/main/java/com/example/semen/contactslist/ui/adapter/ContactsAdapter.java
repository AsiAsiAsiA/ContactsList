package com.example.semen.contactslist.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.semen.contactslist.R;
import com.example.semen.contactslist.domain.Contact;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private ItemClickListener itemClickListener;
    private List<Contact> contacts;
    private Disposable disposable;

    public ContactsAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    //Устанавлиет новый список и обновляет RecyclerView с помощью DiffUtil
    public void updateContacts(List<Contact> contacts) {
        disposable = Single.fromCallable(() ->
                DiffUtil.calculateDiff(new ContactListDiffUtilCallback(contacts, this.contacts)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(diffResult -> {
                    setContacts(contacts);
                    diffResult.dispatchUpdatesTo(this);
                });
    }

    private void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_adapter_item, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, final int position) {
        holder.bind(contacts.get(position));
        holder.itemView.getContext();
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ContactsViewHolder holder) {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onViewDetachedFromWindow(holder);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onClick(String id);
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView name;
        final Context context;

        ContactsViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.contactInfo);
            context = name.getContext();
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        void bind(final Contact contact) {
            name.setText(context.getString(R.string.view_holder_text,
                    context.getString(R.string.id),
                    contact.getId(),
                    context.getString(R.string.name),
                    contact.getName()));
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                itemClickListener.onClick(contacts.get(position).getId());
            }
        }
    }
}
