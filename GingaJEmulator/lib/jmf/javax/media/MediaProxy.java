package javax.media;

public interface MediaProxy extends MediaHandler {

	public abstract javax.media.protocol.DataSource getDataSource()
	throws java.io.IOException, NoDataSourceException;

}
