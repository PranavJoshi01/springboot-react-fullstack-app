import { useEffect, useState } from "react";
import axios from "axios";

function Items() {
  const [items, setItems] = useState([]);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");

  const token = localStorage.getItem("token");

  // 🔹 Fetch items
  const fetchItems = () => {
    axios
      .get("http://localhost:8082/items", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => setItems(res.data))
      .catch((err) => console.error("Error fetching items", err));
  };

  useEffect(() => {
    fetchItems();
  }, []);

  // 🔹 Add item
  const handleAddItem = (e) => {
    e.preventDefault();

    if (!name.trim()) return;

    axios
      .post(
        "http://localhost:8082/items",
        { name, description },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then(() => {
        setName("");
        setDescription("");
        fetchItems();
      })
      .catch((err) => console.error("Error adding item", err));
  };

  // 🔹 Delete item
  const handleDelete = (id) => {
    if (!window.confirm("Are you sure you want to delete this item?")) return;

    axios
      .delete(`http://localhost:8082/items/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then(fetchItems)
      .catch((err) => console.error("Error deleting item", err));
  };

  // 🔹 Edit item
  const handleEdit = (item) => {
    const newName = prompt("Enter new item name", item.name);
    if (!newName) return;

    const newDescription = prompt(
      "Enter new description",
      item.description
    );

    axios
      .put(
        `http://localhost:8082/items/${item.id}`,
        {
          name: newName,
          description: newDescription,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
      .then(fetchItems)
      .catch((err) => console.error("Error updating item", err));
  };

  return (
    <div className="container mt-5">
      <h3 className="mb-4">Items</h3>

      {/* 🔹 Add Item Form */}
      <form onSubmit={handleAddItem} className="mb-4">
        <div className="mb-3">
          <input
            type="text"
            className="form-control"
            placeholder="Item name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </div>

        <div className="mb-3">
          <textarea
            className="form-control"
            placeholder="Item description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            rows="3"
          />
        </div>

        <button type="submit" className="btn btn-primary">
          Add Item
        </button>
      </form>

      {/* 🔹 Items List */}
      {items.length === 0 ? (
        <p>No items found.</p>
      ) : (
        <ul className="list-group">
          {items.map((item) => (
            <li key={item.id} className="list-group-item">
              <h6 className="mb-1">{item.name}</h6>
              <p className="mb-1">{item.description}</p>

              <small className="text-muted d-block mb-2">
                Created by: {item.createdBy} |{" "}
                {item.createdAt
                  ? new Date(item.createdAt).toLocaleString()
                  : ""}
              </small>

              <button
                className="btn btn-warning btn-sm me-2"
                onClick={() => handleEdit(item)}
              >
                Edit
              </button>

              <button
                className="btn btn-danger btn-sm"
                onClick={() => handleDelete(item.id)}
              >
                Delete
              </button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default Items;
