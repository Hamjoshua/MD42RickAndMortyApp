package com.example.md42_rickandmortyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.md42_rickandmortyapp.databinding.RItemBinding



class CharacterAdapter(private val characters: ArrayList<Character>) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>()
{
    companion object {
        const val VIEW_TYPE_HUMAN : Int = 0
        const val VIEW_TYPE_ALIEN : Int = 1
        const val VIEW_TYPE_OTHER : Int = 2
    }
    class CharacterViewHolder(private val itemBinding: RItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)
    {
        fun bind(character: Character){
            character.setImageTo(itemBinding.avatarImage)
            itemBinding.nameTv.text = character.name
            itemBinding.typeTv.text = character.species
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (characters[position].species) {
            "Human" -> VIEW_TYPE_HUMAN
            "Alien" -> VIEW_TYPE_ALIEN
            else -> VIEW_TYPE_OTHER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
       val itemBinding = RItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

       return CharacterViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character: Character = characters[position]
        holder.bind(character)
    }

}