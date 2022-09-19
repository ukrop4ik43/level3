package com.example.myapplication.ui.contactsList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemUserBinding
import com.example.myapplication.extensions.addImage
import com.example.myapplication.ui.model.User


interface OnContactClickListener {
    fun onUserDelete(userPosition: Int)
    fun navigateToDetailFragment(user: User)

}

class UsersAdapter(
    private val onContactClickListener: OnContactClickListener
) :
    ListAdapter<User, UsersAdapter.UsersViewHolder>(object : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return UsersViewHolder(binding, onContactClickListener)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }


    inner class UsersViewHolder(
        private val binding: ItemUserBinding,
        private val onContactClickListener: OnContactClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(user: User) {
            with(binding) {
                tvNameSurname.text = user.name
                tvCareer.text = user.occupy
                if (user.photo.isNotBlank()) {
                    ivAvatar.addImage(user)
                } else {
                    ivAvatar.setImageResource(R.drawable.me)
                }
            }
            setListeners(user)
        }

        private fun setListeners(user: User) {
            with(binding) {
                ibTrashBin.setOnClickListener {
                    onContactClickListener.onUserDelete(absoluteAdapterPosition)
                    Toast.makeText(it.context, "Deleted", Toast.LENGTH_LONG).show()
                }
                root.setOnClickListener {
                    onContactClickListener.navigateToDetailFragment(user)

                }
            }
        }

    }
}


