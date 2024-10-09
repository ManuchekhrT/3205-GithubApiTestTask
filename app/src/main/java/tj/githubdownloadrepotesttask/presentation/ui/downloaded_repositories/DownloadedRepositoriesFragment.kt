package tj.githubdownloadrepotesttask.presentation.ui.downloaded_repositories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tj.githubdownloadrepotesttask.databinding.FragmentDownloadedReposBinding
import tj.githubdownloadrepotesttask.extensions.waitForLayout
import tj.githubdownloadrepotesttask.presentation.model.State

const val TAG_DownloadedReposFragment = "DownloadedReposFragment"

@AndroidEntryPoint
class DownloadedReposFragment : BottomSheetDialogFragment() {

    private val dialogHeightHelper = FullScreenDialogHeightHolder()

    private val binding by lazy {
        FragmentDownloadedReposBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: DownloadedRepositoryAdapter

    private val viewModel: DownloadedRepositoriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.waitForLayout {
            dialogHeightHelper.calculateDialogHeight(requireActivity(), savedInstanceState)
            setDialogViewHeightAndLayoutParams()
        }
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeRepos()
    }

    private fun setupRecyclerView() {
        adapter = DownloadedRepositoryAdapter()
        binding.rvDownloadedRepos.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvDownloadedRepos.adapter = adapter
    }

    private fun observeRepos() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.downloadedReposState.collectLatest {
                    when (it) {
                        is State.Success -> {
                            binding.tvEmptyData.isVisible = false
                            binding.progressBar.isVisible = false
                            binding.rvDownloadedRepos.isVisible = true
                            adapter.submitList(it.data)
                        }

                        is State.Error -> {
                            binding.progressBar.isVisible = false
                            binding.tvEmptyData.isVisible = false
                            binding.rvDownloadedRepos.isVisible = false
                            Toast.makeText(requireContext(), it.message ?: "", Toast.LENGTH_SHORT)
                                .show()
                        }

                        is State.Loading -> {
                            binding.progressBar.isVisible = true
                        }

                        is State.Empty -> {
                            binding.progressBar.isVisible = false
                            binding.rvDownloadedRepos.isVisible = false
                            binding.tvEmptyData.isVisible = true
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }

    private fun setDialogViewHeightAndLayoutParams() {
        val lp = view?.layoutParams ?: FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            dialogHeightHelper.desiredHeight
        )
        lp.height = dialogHeightHelper.desiredHeight
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT
        view?.layoutParams = lp
    }
}