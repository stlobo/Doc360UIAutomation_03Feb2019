package docClassPageObject;
import java.util.ArrayList;

public class ElasticSearchResponseDTO {
	 private float took;
	 private boolean timed_out;
	 _shards _shardsObject;
	 Hits HitsObject;


	 // Getter Methods 

	 public float getTook() {
	  return took;
	 }

	 public boolean getTimed_out() {
	  return timed_out;
	 }

	 public _shards get_shards() {
	  return _shardsObject;
	 }

	 public Hits getHits() {
	  return HitsObject;
	 }

	 // Setter Methods 

	 public void setTook(float took) {
	  this.took = took;
	 }

	 public void setTimed_out(boolean timed_out) {
	  this.timed_out = timed_out;
	 }

	 public void set_shards(_shards _shardsObject) {
	  this._shardsObject = _shardsObject;
	 }

	 public void setHits(Hits hitsObject) {
	  this.HitsObject = hitsObject;
	 }
	}
	 class Hits {
	 private float total;
	 private float max_score;
	 ArrayList < Object > hits = new ArrayList < Object > ();


	 // Getter Methods 

	 public float getTotal() {
	  return total;
	 }

	 public float getMax_score() {
	  return max_score;
	 }

	 // Setter Methods 

	 public void setTotal(float total) {
	  this.total = total;
	 }

	 public void setMax_score(float max_score) {
	  this.max_score = max_score;
	 }
	}
	class _shards {
	 private float total;
	 private float successful;
	 private float skipped;
	 private float failed;


	 // Getter Methods 

	 public float getTotal() {
	  return total;
	 }

	 public float getSuccessful() {
	  return successful;
	 }

	 public float getSkipped() {
	  return skipped;
	 }

	 public float getFailed() {
	  return failed;
	 }

	 // Setter Methods 

	 public void setTotal(float total) {
	  this.total = total;
	 }

	 public void setSuccessful(float successful) {
	  this.successful = successful;
	 }

	 public void setSkipped(float skipped) {
	  this.skipped = skipped;
	 }

	 public void setFailed(float failed) {
	  this.failed = failed;
	 }
	}
