package group7891234.deliverable2.library.item;

public interface ItemVisitor {
	void visitBook(Book book);
	void visitOnlineBook(OnlineBook book);
	void visitNewsLetter(NewsLetter news);
	void visitTextBook(TextBook book);
}
