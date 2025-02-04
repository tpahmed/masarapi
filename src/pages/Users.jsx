import { AppSidebar } from "@/components/sidebar/app-sidebar"
import { SidebarInset, SidebarProvider } from "@/components/ui/sidebar"
import Header from "@/layout/header"
import { useState } from "react"
import { Button } from "@/components/ui/button"
import { AddStudentDialog } from "@/components/user/add-student-dialog"
import { AddTeacherDialog } from "@/components/user/add-teacher-dialog"
import { EditStudentDialog } from "@/components/user/edit-student-dialog"
import { EditTeacherDialog } from "@/components/user/edit-teacher-dialog"
import { DeleteConfirmationDialog } from "@/components/user/delete-confirmation-dialog"
import { DataTable } from "@/components/user/data-table"
import { Checkbox } from "@/components/ui/checkbox"


export default function UserManagement() {
    const [tableType, setTableType] = useState("students")
    const [isStudentDialogOpen, setIsStudentDialogOpen] = useState(false)
  const [isTeacherDialogOpen, setIsTeacherDialogOpen] = useState(false)
  const [isEditStudentDialogOpen, setIsEditStudentDialogOpen] = useState(false)
  const [isEditTeacherDialogOpen, setIsEditTeacherDialogOpen] = useState(false)
  const [isDeleteDialogOpen, setIsDeleteDialogOpen] = useState(false)
  const [editingStudent, setEditingStudent] = useState(null)
  const [editingTeacher, setEditingTeacher] = useState(null)
  const [selectedIds, setSelectedIds] = useState([])
  const [students, setStudents] = useState([
    {
      id: "1",
      name: "John Smith",
      email: "john.smith@school.com",
      level: "Advanced",
      age: 15,
      yearOfInscription: 2023,
      class: "10A",
    },
    {
      id: "2",
      name: "Emma Wilson",
      email: "emma.w@school.com",
      level: "Intermediate",
      age: 14,
      yearOfInscription: 2022,
      class: "9B",
    },
    {
      id: "3",
      name: "Michael Johnson",
      email: "michael.j@school.com",
      level: "Beginner",
      age: 13,
      yearOfInscription: 2023,
      class: "8C",
    },
    {
      id: "4",
      name: "Sophia Lee",
      email: "sophia.l@school.com",
      level: "Advanced",
      age: 16,
      yearOfInscription: 2021,
      class: "11A",
    },
    {
      id: "5",
      name: "Daniel Brown",
      email: "daniel.b@school.com",
      level: "Intermediate",
      age: 15,
      yearOfInscription: 2022,
      class: "10B",
    },
  ])

  const [teachers, setTeachers] = useState([
    {
      id: "1",
      name: "Dr. Sarah Johnson",
      email: "sarah.j@school.com",
      subject: "Mathematics",
      experienceYears: 8,
      qualification: "Ph.D. Mathematics",
    },
    {
      id: "2",
      name: "Prof. Michael Brown",
      email: "m.brown@school.com",
      subject: "Physics",
      experienceYears: 12,
      qualification: "M.Sc. Physics",
    },
  ])

  const studentColumns = [
    {
      id: "select",
      header: ({ table }) => (
        <Checkbox
          checked={table.getIsAllPageRowsSelected()}
          onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
          aria-label="Select all"
        />
      ),
      cell: ({ row }) => (
        <Checkbox
          checked={row.getIsSelected()}
          onCheckedChange={(value) => row.toggleSelected(!!value)}
          aria-label="Select row"
        />
      ),
      enableSorting: false,
      enableHiding: false,
    },
    {
      accessorKey: "name",
      header: "Name",
    },
    {
      accessorKey: "email",
      header: "Email",
    },
    {
      accessorKey: "level",
      header: "Level",
    },
    {
      accessorKey: "age",
      header: "Age",
    },
    {
      accessorKey: "yearOfInscription",
      header: "Year of Inscription",
    },
    {
      accessorKey: "class",
      header: "Class",
    },
  ]

  const teacherColumns = [
    {
      id: "select",
      header: ({ table }) => (
        <Checkbox
          checked={table.getIsAllPageRowsSelected()}
          onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
          aria-label="Select all"
        />
      ),
      cell: ({ row }) => (
        <Checkbox
          checked={row.getIsSelected()}
          onCheckedChange={(value) => row.toggleSelected(!!value)}
          aria-label="Select row"
        />
      ),
      enableSorting: false,
      enableHiding: false,
    },
    {
      accessorKey: "name",
      header: "Name",
    },
    {
      accessorKey: "email",
      header: "Email",
    },
    {
      accessorKey: "subject",
      header: "Subject",
    },
    {
      accessorKey: "experienceYears",
      header: "Experience (Years)",
    },
    {
      accessorKey: "qualification",
      header: "Qualification",
    },
  ]

  const handleDelete = (ids) => {
    setSelectedIds(ids)
    setIsDeleteDialogOpen(true)
  }

  const confirmDelete = () => {
    if (tableType === "students") {
      setStudents(students.filter((student) => !selectedIds.includes(student.id)))
    } else {
      setTeachers(teachers.filter((teacher) => !selectedIds.includes(teacher.id)))
    }
    setIsDeleteDialogOpen(false)
    setSelectedIds([])
  }

  const handleEdit = (id) => {
    if (tableType === "students") {
      const studentToEdit = students.find((student) => student.id === id)
      setEditingStudent(studentToEdit)
      setIsEditStudentDialogOpen(true)
    } else {
      const teacherToEdit = teachers.find((teacher) => teacher.id === id)
      setEditingTeacher(teacherToEdit)
      setIsEditTeacherDialogOpen(true)
    }
  }

  const handleSaveStudent = (updatedStudent) => {
    setStudents(
      students.map((student) => (student.id === updatedStudent.id ? { ...student, ...updatedStudent } : student)),
    )
  }

  const handleSaveTeacher = (updatedTeacher) => {
    setTeachers(
      teachers.map((teacher) => (teacher.id === updatedTeacher.id ? { ...teacher, ...updatedTeacher } : teacher)),
    )
  }

  return (
    <SidebarProvider>
    <div className="flex min-h-screen dark:bg-background">
      <AppSidebar className="border-r border-gray-300 shadow-[2px_0_10px_rgb(107,114,128)]" />
      <SidebarInset className="flex-1 ">
        <div className="flex flex-col bg-gray-200 h-full">
          <Header />
                <div className="p-6">
                <div className="flex justify-between items-center mb-8">
                    <div>
                    <h1 className="text-2xl font-bold">{tableType === "students" ? "Student" : "Teacher"} List</h1>
                    <p className="text-muted-foreground">Manage your {tableType} and their information here.</p>
                    </div>
                    <div className="flex gap-4">
                    <Button variant="outline" onClick={() => setTableType("students")}>
                        Students
                    </Button>
                    <Button variant="outline" onClick={() => setTableType("teachers")}>
                        Teachers
                    </Button>
                    <Button
                        onClick={() => (tableType === "students" ? setIsStudentDialogOpen(true) : setIsTeacherDialogOpen(true))}
                    >
                        Add {tableType === "students" ? "Student" : "Teacher"}
                    </Button>
                    </div>
                </div>

                {tableType === "students" ? (
                    <DataTable
                    columns={studentColumns}
                    data={students}
                    filterColumn="name"
                    onEdit={handleEdit}
                    onDelete={handleDelete}
                    />
                ) : (
                    <DataTable
                    columns={teacherColumns}
                    data={teachers}
                    filterColumn="name"
                    onEdit={handleEdit}
                    onDelete={handleDelete}
                    />
                )}

                <AddStudentDialog open={isStudentDialogOpen} onOpenChange={setIsStudentDialogOpen} />
                <AddTeacherDialog open={isTeacherDialogOpen} onOpenChange={setIsTeacherDialogOpen} />
                <EditStudentDialog
                    open={isEditStudentDialogOpen}
                    onOpenChange={setIsEditStudentDialogOpen}
                    student={editingStudent}
                    onSave={handleSaveStudent}
                />
                <EditTeacherDialog
                    open={isEditTeacherDialogOpen}
                    onOpenChange={setIsEditTeacherDialogOpen}
                    teacher={editingTeacher}
                    onSave={handleSaveTeacher}
                />
                <DeleteConfirmationDialog
                    isOpen={isDeleteDialogOpen}
                    onClose={() => setIsDeleteDialogOpen(false)}
                    onConfirm={confirmDelete}
                    itemType={tableType === "students" ? "student" : "teacher"}
                    itemCount={selectedIds.length}
                />
                </div>
        </div>
        </SidebarInset>
            </div>
        </SidebarProvider>
  )
}

