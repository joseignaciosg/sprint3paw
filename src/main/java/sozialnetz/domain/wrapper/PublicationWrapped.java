package sozialnetz.domain.wrapper;

import sozialnetz.domain.entities.Publication;

public class PublicationWrapped implements Comparable<PublicationWrapped>{

	private Publication publication;
	private boolean likedByUser;
	private boolean sharedByUser;

	public PublicationWrapped(final Publication publication,
			final boolean likedByUser, final boolean sharedByUser) {
		this.publication = publication;
		this.likedByUser = likedByUser;
		this.sharedByUser = sharedByUser;
	}

	public PublicationWrapped(Publication publication) {
		this.publication = publication;
		this.likedByUser = false;
		this.sharedByUser = false;
	}

	public Publication getPublication() {
		return publication;
	}

	public boolean isLikedByUser() {
		return likedByUser;
	}

	public boolean isSharedByUser() {
		return sharedByUser;
	}


	@Override
	public int compareTo(PublicationWrapped o) {
		return this.getPublication().compareTo(o.getPublication());
	}

}
