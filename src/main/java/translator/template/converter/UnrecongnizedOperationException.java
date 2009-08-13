package translator.template.converter;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 12.08.2009
 * Time: 2:14:05
 * Note: It's kind of fun to do the impossible
 */
class UnrecognizedOperationException extends Exception{
    public UnrecognizedOperationException(String message) {
        super(message);
    }
}
