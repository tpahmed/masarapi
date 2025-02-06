import { Button } from "@/components/ui/button"
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogFooter,
} from "@/components/ui/dialog"
import { Label } from "@/components/ui/label"
import { Input } from "@/components/ui/input"

export function GradesDialog({ open, onOpenChange, selectedStudents, onSubmit }) {
  const handleSubmit = (e) => {
    e.preventDefault()
    const formData = new FormData(e.target)
    onSubmit({
      exam1: formData.get("exam1"),
      exam2: formData.get("exam2"),
      final: formData.get("final"),
      studentIds: selectedStudents,
    })
  }

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Update Grades</DialogTitle>
        </DialogHeader>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="space-y-2">
            <Label>Exam 1</Label>
            <Input
              type="number"
              name="exam1"
              min="0"
              max="100"
              required
              placeholder="Enter grade (0-100)"
            />
          </div>
          <div className="space-y-2">
            <Label>Exam 2</Label>
            <Input
              type="number"
              name="exam2"
              min="0"
              max="100"
              required
              placeholder="Enter grade (0-100)"
            />
          </div>
          <div className="space-y-2">
            <Label>Final Exam</Label>
            <Input
              type="number"
              name="final"
              min="0"
              max="100"
              required
              placeholder="Enter grade (0-100)"
            />
          </div>
          <DialogFooter>
            <Button type="submit">Save Grades</Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  )
}