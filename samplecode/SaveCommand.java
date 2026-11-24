public class SaveCommand extends Command {
  private final String fileName;
  public SaveCommand(String fileName) {
    this.fileName = fileName;
  }
  @Override
  public void execute() {
    model.save(fileName);
  }
  @Override
  public  boolean undo() {
    return false;
  }
  @Override
  public  boolean redo() {
    return false;
  }
}
