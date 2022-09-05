package com.example.myapplication.ui.contactsList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemUserBinding
import com.example.myapplication.ui.model.User


interface UserActionListener {
    fun onUserDelete(userPosition: Int)
}

class UsersAdapter(private val actionListener: UserActionListener) :
    ListAdapter<User, UsersAdapter.UsersViewHolder>(object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }) {

//    private lateinit var mListener: onItemClickListener
//
//    interface onItemClickListener {
//        fun onItemClick(position: Int)
//    }

//
//    fun setOnItemClickListener(listener: onItemClickListener) {
//        mListener = listener
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
//        binding.trashBinIB.setOnClickListener(this)
        return UsersViewHolder(binding, actionListener)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    class UsersViewHolder(
        private val binding: ItemUserBinding,
        private val userActionListener: UserActionListener
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bindTo(user: User) {
            with(binding) {
//                itemView.tag = user
//                trashBinIB.tag = user
                nameSurnameTV.text = user.name
                careerTV.text = user.occupy
                if (user.photo.isNotBlank()) {
                    avatarIV.addImage(user)
                } else {
                    avatarIV.setImageResource(R.drawable.me)
                }
            }
            setListeners()
        }

        private fun setListeners() {
            binding.root.setOnClickListener {
                userActionListener.onUserDelete(absoluteAdapterPosition)
            }
            binding.trashBinIB.setOnClickListener {
//                val user = v.tag as User
//                val imageButton = ImageButton(v.context)
//                imageButton.setOnClickListener {
//                }
//                val context = v.context
//                when (v.id) {
//                    R.id.trashBinIB -> {
//                val indexToDelete = absoluteAdapterPosition
                userActionListener.onUserDelete(absoluteAdapterPosition )

//                notifyItemRemoved(indexToDelete)
                Toast.makeText(it.context, "Deleted", Toast.LENGTH_LONG).show()
//                    }
            }
        }
    }
}





private fun ImageView.addImage(user: User) {
    Glide.with(this.context)
        .load(user.photo)
        .circleCrop()
        .placeholder(R.drawable.me)
        .error(R.drawable.me)
        .into(this)
}
