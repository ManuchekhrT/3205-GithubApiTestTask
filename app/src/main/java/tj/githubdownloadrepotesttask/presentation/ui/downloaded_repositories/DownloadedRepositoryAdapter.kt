package tj.githubdownloadrepotesttask.presentation.ui.downloaded_repositories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tj.githubdownloadrepotesttask.R
import tj.githubdownloadrepotesttask.databinding.ItemRepoBinding
import tj.githubdownloadrepotesttask.presentation.model.Repository

class DownloadedRepositoryAdapter : ListAdapter<Repository, DownloadedRepositoryAdapter.DownloadedRepoViewHolder>(
    DownloadedRepoDiffCallBack()
) {

    private class DownloadedRepoDiffCallBack : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadedRepoViewHolder {
        val view = ItemRepoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DownloadedRepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DownloadedRepoViewHolder, position: Int) {
        val model = currentList[position]
        model?.let {
            holder.onBind(it)
        }
    }

    inner class DownloadedRepoViewHolder(private val itemRepoBinding: ItemRepoBinding) :
        RecyclerView.ViewHolder(itemRepoBinding.root) {

        fun onBind(model: Repository) {
            itemRepoBinding.tvLogin.text = model.ownerLogin
            itemRepoBinding.tvRepoName.text = model.name
            itemRepoBinding.tvRepoUrl.text = model.htmlUrl
            itemRepoBinding.tvDownload.text =
                itemRepoBinding.root.context.getString(R.string.downloaded)
            itemRepoBinding.ivDownloadIcon.setImageResource(R.drawable.ic_file_download_done)
            itemRepoBinding.ivDownloadIcon.isVisible = true
            itemRepoBinding.downloadContainer.isVisible = true
        }
    }
}